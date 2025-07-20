package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ResponseBodyAssertionsImpl.
 * Tests response body assertion functionality including various assertion types.
 */
class ResponseBodyAssertionsImplTest {

    private ResponseBodyAssertionsImpl responseBodyAssertions;
    
    @Mock
    private TestCaseRunContext mockContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateWithActualAndContext() {
        // Given
        String actual = "test response";

        // When
        ResponseBodyAssertionsImpl assertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // Then
        assertNotNull(assertions);
        assertEquals(actual, assertions.getActual());
    }

    @Test
    void shouldAssertEqualsToWithString() {
        // Given
        String actual = "Hello World";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        ResponseBodyAssertionsImpl result = (ResponseBodyAssertionsImpl) responseBodyAssertions.equalsTo("Hello World");
        assertSame(responseBodyAssertions, result); // Should return same instance for fluent API
    }

    @Test
    void shouldFailEqualsToWithDifferentValue() {
        // Given
        String actual = "Hello World";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.equalsTo("Hello Universe");
        });
        assertTrue(exception.getMessage().contains("Expected: Hello Universe"));
        assertTrue(exception.getMessage().contains("but was: Hello World"));
    }

    @Test
    void shouldAssertContainsText() {
        // Given
        String actual = "The quick brown fox jumps over the lazy dog";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.contains("quick brown");
        });
    }

    @Test
    void shouldFailContainsWithMissingText() {
        // Given
        String actual = "The quick brown fox";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.contains("lazy dog");
        });
        assertTrue(exception.getMessage().contains("Expected response body to contain: lazy dog"));
    }

    @Test
    void shouldAssertStartsWith() {
        // Given
        String actual = "Hello World";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.startsWith("Hello");
        });
    }

    @Test
    void shouldFailStartsWithWrongPrefix() {
        // Given
        String actual = "Hello World";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.startsWith("World");
        });
        assertTrue(exception.getMessage().contains("Expected response body to start with: World"));
    }

    @Test
    void shouldAssertEndsWith() {
        // Given
        String actual = "Hello World";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.endsWith("World");
        });
    }

    @Test
    void shouldFailEndsWithWrongSuffix() {
        // Given
        String actual = "Hello World";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.endsWith("Hello");
        });
        assertTrue(exception.getMessage().contains("Expected response body to end with: Hello"));
    }

    @Test
    void shouldAssertMatchesPattern() {
        // Given
        String actual = "user123";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.matches("user\\d+");
        });
    }

    @Test
    void shouldFailMatchesWithInvalidPattern() {
        // Given
        String actual = "user123";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.matches("admin\\d+");
        });
        assertTrue(exception.getMessage().contains("Expected response body to match pattern: admin\\d+"));
    }

    @Test
    void shouldAssertSizeCorrectly() {
        // Given
        String actual = "Hello";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.size(5);
        });
    }

    @Test
    void shouldFailSizeWithWrongLength() {
        // Given
        String actual = "Hello";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.size(10);
        });
        assertTrue(exception.getMessage().contains("Expected response body size: 10"));
        assertTrue(exception.getMessage().contains("but was: 5"));
    }

    @Test
    void shouldAssertIsEmpty() {
        // Given
        String actual = "";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.isEmpty();
        });
    }

    @Test
    void shouldFailIsEmptyWithNonEmptyString() {
        // Given
        String actual = "Not Empty";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.isEmpty();
        });
        assertTrue(exception.getMessage().contains("Expected response body to be empty"));
        assertTrue(exception.getMessage().contains("but was: Not Empty"));
    }

    @Test
    void shouldAssertIsNotEmpty() {
        // Given
        String actual = "Not Empty";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.isNotEmpty();
        });
    }

    @Test
    void shouldFailIsNotEmptyWithEmptyString() {
        // Given
        String actual = "";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.isNotEmpty();
        });
        assertEquals("Expected response body to not be empty", exception.getMessage());
    }

    @Test
    void shouldHandleNullActualValue() {
        // Given
        responseBodyAssertions = new ResponseBodyAssertionsImpl(null, mockContext);

        // When & Then
        assertNull(responseBodyAssertions.getActual());
        
        // Should treat null as empty string for assertions
        assertDoesNotThrow(() -> {
            responseBodyAssertions.isEmpty();
        });
        
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.isNotEmpty();
        });
        assertEquals("Expected response body to not be empty", exception.getMessage());
    }

    @Test
    void shouldHandleNonStringActualValue() {
        // Given
        Integer actual = 12345;
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertEquals(actual, responseBodyAssertions.getActual());
        assertDoesNotThrow(() -> {
            responseBodyAssertions.contains("234");
            responseBodyAssertions.startsWith("123");
            responseBodyAssertions.endsWith("345");
            responseBodyAssertions.size(5);
        });
    }

    @Test
    void shouldChainAssertions() {
        // Given
        String actual = "Hello World Test";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            ResponseBodyAssertionsImpl result = (ResponseBodyAssertionsImpl) responseBodyAssertions
                .contains("World")
                .startsWith("Hello")
                .endsWith("Test")
                .size(16)
                .isNotEmpty();
                
            assertSame(responseBodyAssertions, result);
        });
    }

    @Test
    void shouldHandleComplexRegexPattern() {
        // Given
        String actual = "user@example.com";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.matches("\\w+@\\w+\\.\\w+");
        });
    }

    @Test
    void shouldHandleSpecialCharacters() {
        // Given
        String actual = "Special chars: àáâãäåæçèéêë 🌍";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions
                .contains("àáâãäåæçèéêë")
                .contains("🌍")
                .startsWith("Special")
                .endsWith("🌍");
        });
    }

    @Test
    void shouldHandleLargeContent() {
        // Given
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeContent.append("Large content line ").append(i).append(". ");
        }
        responseBodyAssertions = new ResponseBodyAssertionsImpl(largeContent.toString(), mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions
                .contains("Large content line 500")
                .startsWith("Large content line 0")
                .endsWith("999. ")
                .isNotEmpty();
        });
        assertTrue(responseBodyAssertions.getActual().toString().length() > 10000);
    }

    @Test
    void shouldHandleCaseSensitiveOperations() {
        // Given
        String actual = "Hello World";
        responseBodyAssertions = new ResponseBodyAssertionsImpl(actual, mockContext);

        // When & Then
        assertDoesNotThrow(() -> {
            responseBodyAssertions.contains("Hello");
        });
        
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            responseBodyAssertions.contains("hello");
        });
        assertTrue(exception.getMessage().contains("Expected response body to contain: hello"));
    }
}
