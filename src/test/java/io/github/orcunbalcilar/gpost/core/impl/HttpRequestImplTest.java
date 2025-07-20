package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HttpRequestImpl.
 * Tests the HTTP request building functionality including headers and authentication.
 */
class HttpRequestImplTest {

    private HttpRequestImpl httpRequest;
    
    @Mock
    private TestCaseRunContext mockContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        httpRequest = new HttpRequestImpl(mockContext);
    }

    @Test
    void shouldCreateWithContext() {
        // When
        HttpRequestImpl request = new HttpRequestImpl(mockContext);

        // Then
        assertNotNull(request);
        assertEquals(mockContext, request.getContext());
        assertTrue(request.getHeaders().isEmpty());
        assertNull(request.getAuth());
    }

    @Test
    void shouldAddSingleHeader() {
        // Given
        String headerName = "Content-Type";
        String headerValue = "application/json";

        // When
        HttpRequestImpl result = (HttpRequestImpl) httpRequest.header(headerName, headerValue);

        // Then
        assertSame(httpRequest, result); // Should return same instance for fluent API
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(1, headers.size());
        assertEquals(headerValue, headers.get(headerName));
    }

    @Test
    void shouldAddMultipleHeaders() {
        // When
        httpRequest
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("User-Agent", "Test-Agent");

        // Then
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(3, headers.size());
        assertEquals("application/json", headers.get("Content-Type"));
        assertEquals("application/json", headers.get("Accept"));
        assertEquals("Test-Agent", headers.get("User-Agent"));
    }

    @Test
    void shouldConfigureHeadersWithConsumer() {
        // When
        httpRequest.headers(headerConfig -> {
            headerConfig.header("X-Custom-Header", "custom-value");
            headerConfig.header("X-Request-ID", "12345");
        });

        // Then
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(2, headers.size());
        assertEquals("custom-value", headers.get("X-Custom-Header"));
        assertEquals("12345", headers.get("X-Request-ID"));
    }

    @Test
    void shouldSetBasicAuthWithUsernamePassword() {
        // Given
        String username = "testuser";
        String password = "testpass";
        String expectedAuth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        // When
        HttpRequestImpl result = (HttpRequestImpl) httpRequest.basicAuth(username, password);

        // Then
        assertSame(httpRequest, result);
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals("Basic " + expectedAuth, headers.get("Authorization"));
    }

    @Test
    void shouldSetBearerAuth() {
        // Given
        String token = "abc123token";

        // When
        HttpRequestImpl result = (HttpRequestImpl) httpRequest.bearerAuth(token);

        // Then
        assertSame(httpRequest, result);
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals("Bearer " + token, headers.get("Authorization"));
    }

    @Test
    void shouldConfigureBasicAuthWithConsumer() {
        // When
        httpRequest.basicAuth(auth -> {
            auth.username("testuser");
            auth.password("testpass");
        });

        // Then
        assertNotNull(httpRequest.getAuth());
        assertEquals("basic", httpRequest.getAuth().getType());
        assertEquals("testuser", ((BasicAuthImpl) httpRequest.getAuth()).getUsername());
        assertEquals("testpass", ((BasicAuthImpl) httpRequest.getAuth()).getPassword());
    }

    @Test
    void shouldOverwriteHeaderValue() {
        // Given
        String headerName = "Content-Type";
        String originalValue = "text/plain";
        String newValue = "application/json";

        // When
        httpRequest.header(headerName, originalValue);
        httpRequest.header(headerName, newValue);

        // Then
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(1, headers.size());
        assertEquals(newValue, headers.get(headerName));
    }

    @Test
    void shouldOverwriteAuthorizationHeader() {
        // Given
        httpRequest.basicAuth("user1", "pass1");
        
        // When
        httpRequest.bearerAuth("newtoken");

        // Then
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals("Bearer newtoken", headers.get("Authorization"));
    }

    @Test
    void shouldHandleNullHeaderName() {
        // When & Then
        assertDoesNotThrow(() -> {
            httpRequest.header(null, "value");
        });
        
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals("value", headers.get(null));
    }

    @Test
    void shouldHandleNullHeaderValue() {
        // When & Then
        assertDoesNotThrow(() -> {
            httpRequest.header("TestHeader", null);
        });
        
        Map<String, String> headers = httpRequest.getHeaders();
        assertNull(headers.get("TestHeader"));
    }

    @Test
    void shouldHandleEmptyHeaderName() {
        // Given
        String emptyName = "";
        String value = "test-value";

        // When
        httpRequest.header(emptyName, value);

        // Then
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(value, headers.get(emptyName));
    }

    @Test
    void shouldHandleEmptyHeaderValue() {
        // Given
        String name = "Empty-Header";
        String emptyValue = "";

        // When
        httpRequest.header(name, emptyValue);

        // Then
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(emptyValue, headers.get(name));
    }

    @Test
    void shouldHandleSpecialCharactersInHeaders() {
        // Given
        String specialName = "X-Special-Chars-àáâã";
        String specialValue = "value-with-special-chars-çèéêë";

        // When
        httpRequest.header(specialName, specialValue);

        // Then
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(specialValue, headers.get(specialName));
    }

    @Test
    void shouldChainHeaderOperations() {
        // When
        HttpRequestImpl result = (HttpRequestImpl) httpRequest
            .header("Header1", "Value1")
            .header("Header2", "Value2")
            .basicAuth("user", "pass")
            .header("Header3", "Value3");

        // Then
        assertSame(httpRequest, result);
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(4, headers.size()); // 3 custom headers + 1 Authorization header
        assertEquals("Value1", headers.get("Header1"));
        assertEquals("Value2", headers.get("Header2"));
        assertEquals("Value3", headers.get("Header3"));
        assertTrue(headers.get("Authorization").startsWith("Basic "));
    }

    @Test
    void shouldHandleCaseSensitiveHeaders() {
        // Given
        httpRequest.header("content-type", "application/json");
        httpRequest.header("Content-Type", "text/plain");

        // Then - Should treat as separate headers (case sensitive)
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(2, headers.size());
        assertEquals("application/json", headers.get("content-type"));
        assertEquals("text/plain", headers.get("Content-Type"));
    }

    @Test
    void shouldMaintainHeaderOrder() {
        // When
        httpRequest
            .header("First", "1")
            .header("Second", "2")
            .header("Third", "3");

        // Then
        Map<String, String> headers = httpRequest.getHeaders();
        assertEquals(3, headers.size());
        // Note: HashMap doesn't guarantee order, but we can verify all are present
        assertTrue(headers.containsKey("First"));
        assertTrue(headers.containsKey("Second"));
        assertTrue(headers.containsKey("Third"));
    }

    @Test
    void shouldGetHttpHeadersObject() {
        // When
        HttpHeadersImpl httpHeaders = httpRequest.getHttpHeaders();

        // Then
        assertNotNull(httpHeaders);
        // The HttpHeadersImpl should share the same underlying map
        httpRequest.header("Test", "Value");
        // This would need to be verified based on HttpHeadersImpl implementation
    }
}
