package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RequestBodyImpl.
 * Tests the request body handling functionality including content and content type management.
 */
class RequestBodyImplTest {

    private RequestBodyImpl requestBody;
    
    @Mock
    private TestCaseRunContext mockContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requestBody = new RequestBodyImpl(mockContext);
    }

    @Test
    void shouldCreateWithContextOnly() {
        // When
        RequestBodyImpl body = new RequestBodyImpl(mockContext);

        // Then
        assertNotNull(body);
        assertEquals(mockContext, body.getContext());
        assertNull(body.getContent());
        assertNull(body.getContentType());
    }

    @Test
    void shouldCreateWithAllParameters() {
        // Given
        String content = "test content";
        String contentType = "application/json";

        // When
        RequestBodyImpl body = new RequestBodyImpl(mockContext, content, contentType);

        // Then
        assertEquals(mockContext, body.getContext());
        assertEquals(content, body.getContent());
        assertEquals(contentType, body.getContentType());
    }

    @Test
    void shouldSetAndGetContent() {
        // Given
        String content = "Hello World";

        // When
        requestBody.setContent(content);

        // Then
        assertEquals(content, requestBody.getContent());
    }

    @Test
    void shouldSetAndGetContentType() {
        // Given
        String contentType = "text/plain";

        // When
        requestBody.setContentType(contentType);

        // Then
        assertEquals(contentType, requestBody.getContentType());
    }

    @Test
    void shouldBuildReturnContent() {
        // Given
        String content = "Test build content";
        requestBody.setContent(content);

        // When
        String result = requestBody.build();

        // Then
        assertEquals(content, result);
    }

    @Test
    void shouldHandleNullContent() {
        // When
        requestBody.setContent(null);

        // Then
        assertNull(requestBody.getContent());
        assertNull(requestBody.build());
    }

    @Test
    void shouldHandleEmptyContent() {
        // Given
        String emptyContent = "";

        // When
        requestBody.setContent(emptyContent);

        // Then
        assertEquals(emptyContent, requestBody.getContent());
        assertEquals(emptyContent, requestBody.build());
    }

    @Test
    void shouldHandleJsonContent() {
        // Given
        String jsonContent = "{\"key\":\"value\",\"number\":42,\"active\":true}";
        String jsonContentType = "application/json";

        // When
        requestBody.setContent(jsonContent);
        requestBody.setContentType(jsonContentType);

        // Then
        assertEquals(jsonContent, requestBody.getContent());
        assertEquals(jsonContentType, requestBody.getContentType());
        assertEquals(jsonContent, requestBody.build());
    }

    @Test
    void shouldHandleXmlContent() {
        // Given
        String xmlContent = "<?xml version=\"1.0\"?><root><element>value</element></root>";
        String xmlContentType = "application/xml";

        // When
        requestBody.setContent(xmlContent);
        requestBody.setContentType(xmlContentType);

        // Then
        assertEquals(xmlContent, requestBody.getContent());
        assertEquals(xmlContentType, requestBody.getContentType());
        assertEquals(xmlContent, requestBody.build());
    }

    @Test
    void shouldHandleFormDataContent() {
        // Given
        String formContent = "field1=value1&field2=value2";
        String formContentType = "application/x-www-form-urlencoded";

        // When
        requestBody.setContent(formContent);
        requestBody.setContentType(formContentType);

        // Then
        assertEquals(formContent, requestBody.getContent());
        assertEquals(formContentType, requestBody.getContentType());
        assertEquals(formContent, requestBody.build());
    }

    @Test
    void shouldOverwriteContent() {
        // Given
        String originalContent = "original content";
        String newContent = "new content";
        
        requestBody.setContent(originalContent);

        // When
        requestBody.setContent(newContent);

        // Then
        assertEquals(newContent, requestBody.getContent());
        assertEquals(newContent, requestBody.build());
    }

    @Test
    void shouldOverwriteContentType() {
        // Given
        String originalContentType = "text/plain";
        String newContentType = "application/json";
        
        requestBody.setContentType(originalContentType);

        // When
        requestBody.setContentType(newContentType);

        // Then
        assertEquals(newContentType, requestBody.getContentType());
    }

    @Test
    void shouldHandleSpecialCharactersInContent() {
        // Given
        String specialContent = "Special chars: àáâãäåçèéêë ñøùúû ¡¢£¤¥¦§¨©";
        String contentType = "text/plain; charset=utf-8";

        // When
        requestBody.setContent(specialContent);
        requestBody.setContentType(contentType);

        // Then
        assertEquals(specialContent, requestBody.getContent());
        assertEquals(contentType, requestBody.getContentType());
        assertEquals(specialContent, requestBody.build());
    }

    @Test
    void shouldHandleLargeContent() {
        // Given
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeContent.append("This is line ").append(i).append(" of large content.\n");
        }
        String content = largeContent.toString();

        // When
        requestBody.setContent(content);

        // Then
        assertEquals(content, requestBody.getContent());
        assertEquals(content, requestBody.build());
        assertTrue(requestBody.getContent().length() > 10000);
    }

    @Test
    void shouldMaintainContextReference() {
        // Given
        RequestBodyImpl body1 = new RequestBodyImpl(mockContext);
        RequestBodyImpl body2 = new RequestBodyImpl(mockContext, "content", "type");

        // When & Then
        assertSame(mockContext, body1.getContext());
        assertSame(mockContext, body2.getContext());
    }
}
