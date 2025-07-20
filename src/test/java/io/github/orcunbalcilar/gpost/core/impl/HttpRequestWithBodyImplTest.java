package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.RequestBody;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HttpRequestWithBodyImpl.
 * Tests HTTP request with body functionality including various body types.
 */
class HttpRequestWithBodyImplTest {

    private HttpRequestWithBodyImpl httpRequestWithBody;
    
    @Mock
    private TestCaseRunContext mockContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        httpRequestWithBody = new HttpRequestWithBodyImpl(mockContext);
    }

    @Test
    void shouldCreateWithContext() {
        // When
        HttpRequestWithBodyImpl request = new HttpRequestWithBodyImpl(mockContext);

        // Then
        assertNotNull(request);
        assertEquals(mockContext, request.getContext());
        assertNotNull(request.getBody());
        assertNull(request.getRequestBody());
    }

    @Test
    void shouldInheritHttpRequestFeatures() {
        // When
        httpRequestWithBody
            .header("Content-Type", "application/json")
            .basicAuth("user", "pass");

        // Then
        assertNotNull(httpRequestWithBody.getHeaders());
        assertEquals("application/json", httpRequestWithBody.getHeaders().get("Content-Type"));
        assertTrue(httpRequestWithBody.getHeaders().containsKey("Authorization"));
    }

    @Test
    void shouldConfigureBodyWithConsumer() {
        // When
        HttpRequestWithBodyImpl result = (HttpRequestWithBodyImpl) httpRequestWithBody.body(body -> {
            body.json("{\"key\": \"value\"}");
        });

        // Then
        assertSame(httpRequestWithBody, result); // Should return same instance for fluent API
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertNotNull(body);
        assertEquals("{\"key\": \"value\"}", body.getContent());
        assertEquals("application/json", body.getContentType());
    }

    @Test
    void shouldSetJsonBodyDirectly() {
        // Given
        String jsonContent = "{\"name\": \"test\", \"value\": 123}";

        // When
        HttpRequestWithBodyImpl result = (HttpRequestWithBodyImpl) httpRequestWithBody.jsonBody(jsonContent);

        // Then
        assertSame(httpRequestWithBody, result);
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals(jsonContent, body.getContent());
        assertEquals("application/json", body.getContentType());
    }

    @Test
    void shouldSetXmlBodyDirectly() {
        // Given
        String xmlContent = "<root><item>test</item></root>";

        // When
        HttpRequestWithBodyImpl result = (HttpRequestWithBodyImpl) httpRequestWithBody.xmlBody(xmlContent);

        // Then
        assertSame(httpRequestWithBody, result);
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals(xmlContent, body.getContent());
        assertEquals("application/xml", body.getContentType());
    }

    @Test
    void shouldSetTextBodyDirectly() {
        // Given
        String textContent = "This is plain text content";

        // When
        HttpRequestWithBodyImpl result = (HttpRequestWithBodyImpl) httpRequestWithBody.textBody(textContent);

        // Then
        assertSame(httpRequestWithBody, result);
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals(textContent, body.getContent());
        assertEquals("text/plain", body.getContentType());
    }

    @Test
    void shouldConfigureRequestBodyWithBuilder() {
        // When
        HttpRequestWithBodyImpl result = (HttpRequestWithBodyImpl) httpRequestWithBody.requestBody(builder -> {
            builder.json("{\"test\": true}");
        });

        // Then
        assertSame(httpRequestWithBody, result);
        // Note: Current implementation doesn't fully integrate builder with body
        // This test verifies the method doesn't throw and returns the instance
    }

    @Test
    void shouldChainBodyOperations() {
        // When
        httpRequestWithBody
            .header("X-Custom", "test")
            .header("Accept", "application/json");
            
        httpRequestWithBody.jsonBody("{\"initial\": true}");
        httpRequestWithBody.xmlBody("<updated>true</updated>");

        // Then
        assertEquals(2, httpRequestWithBody.getHeaders().size());
        assertEquals("test", httpRequestWithBody.getHeaders().get("X-Custom"));
        assertEquals("application/json", httpRequestWithBody.getHeaders().get("Accept"));
        
        // Last body operation should win
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals("<updated>true</updated>", body.getContent());
        assertEquals("application/xml", body.getContentType());
    }

    @Test
    void shouldHandleEmptyJsonBody() {
        // When
        httpRequestWithBody.jsonBody("");

        // Then
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals("", body.getContent());
        assertEquals("application/json", body.getContentType());
    }

    @Test
    void shouldHandleNullJsonBody() {
        // When & Then
        assertDoesNotThrow(() -> {
            httpRequestWithBody.jsonBody(null);
        });
        
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertNull(body.getContent());
        assertEquals("application/json", body.getContentType());
    }

    @Test
    void shouldHandleEmptyXmlBody() {
        // When
        httpRequestWithBody.xmlBody("");

        // Then
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals("", body.getContent());
        assertEquals("application/xml", body.getContentType());
    }

    @Test
    void shouldHandleNullXmlBody() {
        // When & Then
        assertDoesNotThrow(() -> {
            httpRequestWithBody.xmlBody(null);
        });
        
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertNull(body.getContent());
        assertEquals("application/xml", body.getContentType());
    }

    @Test
    void shouldHandleEmptyTextBody() {
        // When
        httpRequestWithBody.textBody("");

        // Then
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals("", body.getContent());
        assertEquals("text/plain", body.getContentType());
    }

    @Test
    void shouldHandleNullTextBody() {
        // When & Then
        assertDoesNotThrow(() -> {
            httpRequestWithBody.textBody(null);
        });
        
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertNull(body.getContent());
        assertEquals("text/plain", body.getContentType());
    }

    @Test
    void shouldHandleLargeJsonBody() {
        // Given
        StringBuilder largeJson = new StringBuilder("{\"data\": \"");
        for (int i = 0; i < 1000; i++) {
            largeJson.append("This is a large JSON body with repeated content. ");
        }
        largeJson.append("\"}");

        // When
        httpRequestWithBody.jsonBody(largeJson.toString());

        // Then
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals(largeJson.toString(), body.getContent());
        assertEquals("application/json", body.getContentType());
        assertTrue(body.getContent().length() > 10000);
    }

    @Test
    void shouldHandleSpecialCharactersInBody() {
        // Given
        String jsonWithSpecialChars = "{\"message\": \"Héllo Wörld! 🌍 Testing spëcial chars: àáâãäåæçèéêë\"}";

        // When
        httpRequestWithBody.jsonBody(jsonWithSpecialChars);

        // Then
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals(jsonWithSpecialChars, body.getContent());
        assertEquals("application/json", body.getContentType());
    }

    @Test
    void shouldSetAndGetRequestBody() {
        // Given
        RequestBody mockRequestBody = new RequestBodyImpl(mockContext);

        // When
        httpRequestWithBody.setRequestBody(mockRequestBody);

        // Then
        assertSame(mockRequestBody, httpRequestWithBody.getRequestBody());
    }

    @Test
    void shouldOverwriteBodyContent() {
        // Given
        httpRequestWithBody.jsonBody("{\"first\": true}");
        
        // When
        httpRequestWithBody.textBody("New text content");

        // Then
        HttpBodyImpl body = httpRequestWithBody.getBody();
        assertEquals("New text content", body.getContent());
        assertEquals("text/plain", body.getContentType());
    }

    @Test
    void shouldMaintainBodyReference() {
        // Given
        HttpBodyImpl originalBody = httpRequestWithBody.getBody();

        // When
        httpRequestWithBody.jsonBody("{\"test\": true}");

        // Then
        assertSame(originalBody, httpRequestWithBody.getBody());
        assertEquals("{\"test\": true}", originalBody.getContent());
    }
}
