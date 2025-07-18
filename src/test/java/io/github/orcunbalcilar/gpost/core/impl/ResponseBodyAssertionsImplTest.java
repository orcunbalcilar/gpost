package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.ResponseBodyAssertions;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseBodyAssertionsImplTest {
    
    private TestCaseRunContext context;
    
    @BeforeEach
    void setUp() {
        context = new TestCaseRunContextImpl();
    }
    
    @Test
    void testEqualsToSuccess() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("test content", context);
        
        assertSame(assertions, assertions.equalsTo("test content"));
    }
    
    @Test
    void testEqualsToFailure() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("test content", context);
        
        AssertionError error = assertThrows(AssertionError.class, () -> 
            assertions.equalsTo("different content"));
        assertTrue(error.getMessage().contains("Expected: different content"));
        assertTrue(error.getMessage().contains("but was: test content"));
    }
    
    @Test
    void testContainsSuccess() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello World", context);
        
        assertSame(assertions, assertions.contains("Hello"));
        assertSame(assertions, assertions.contains("World"));
        assertSame(assertions, assertions.contains("lo Wo"));
    }
    
    @Test
    void testContainsFailure() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello World", context);
        
        AssertionError error = assertThrows(AssertionError.class, () -> 
            assertions.contains("Goodbye"));
        assertTrue(error.getMessage().contains("Expected response body to contain: Goodbye"));
    }
    
    @Test
    void testStartsWithSuccess() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello World", context);
        
        assertSame(assertions, assertions.startsWith("Hello"));
        assertSame(assertions, assertions.startsWith("H"));
    }
    
    @Test
    void testStartsWithFailure() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello World", context);
        
        AssertionError error = assertThrows(AssertionError.class, () -> 
            assertions.startsWith("World"));
        assertTrue(error.getMessage().contains("Expected response body to start with: World"));
    }
    
    @Test
    void testEndsWithSuccess() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello World", context);
        
        assertSame(assertions, assertions.endsWith("World"));
        assertSame(assertions, assertions.endsWith("d"));
    }
    
    @Test
    void testEndsWithFailure() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello World", context);
        
        AssertionError error = assertThrows(AssertionError.class, () -> 
            assertions.endsWith("Hello"));
        assertTrue(error.getMessage().contains("Expected response body to end with: Hello"));
    }
    
    @Test
    void testMatchesSuccess() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello123", context);
        
        assertSame(assertions, assertions.matches("Hello\\d+"));
        assertSame(assertions, assertions.matches(".*123"));
    }
    
    @Test
    void testMatchesFailure() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello123", context);
        
        AssertionError error = assertThrows(AssertionError.class, () -> 
            assertions.matches("\\d+"));
        assertTrue(error.getMessage().contains("Expected response body to match pattern"));
    }
    
    @Test
    void testSizeSuccess() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello", context);
        
        assertSame(assertions, assertions.size(5));
    }
    
    @Test
    void testSizeFailure() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello", context);
        
        AssertionError error = assertThrows(AssertionError.class, () -> 
            assertions.size(3));
        assertTrue(error.getMessage().contains("Expected response body size: 3"));
        assertTrue(error.getMessage().contains("but was: 5"));
    }
    
    @Test
    void testIsEmptySuccess() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("", context);
        
        assertSame(assertions, assertions.isEmpty());
    }
    
    @Test
    void testIsEmptyFailure() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("not empty", context);
        
        AssertionError error = assertThrows(AssertionError.class, () -> 
            assertions.isEmpty());
        assertTrue(error.getMessage().contains("Expected response body to be empty"));
    }
    
    @Test
    void testIsNotEmptySuccess() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("not empty", context);
        
        assertSame(assertions, assertions.isNotEmpty());
    }
    
    @Test
    void testIsNotEmptyFailure() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("", context);
        
        AssertionError error = assertThrows(AssertionError.class, () -> 
            assertions.isNotEmpty());
        assertTrue(error.getMessage().contains("Expected response body to not be empty"));
    }
    
    @Test
    void testGetActual() {
        String actualContent = "test content";
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl(actualContent, context);
        
        assertEquals(actualContent, assertions.getActual());
    }
    
    @Test
    void testNullActual() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl(null, context);
        
        assertNull(assertions.getActual());
        assertDoesNotThrow(() -> assertions.isEmpty());
        assertDoesNotThrow(() -> assertions.size(0));
    }
    
    @Test
    void testMethodChaining() {
        ResponseBodyAssertions assertions = new ResponseBodyAssertionsImpl("Hello World", context);
        
        // Test that methods can be chained together
        assertSame(assertions, assertions
            .contains("Hello")
            .startsWith("Hello")
            .endsWith("World")
            .size(11));
    }
}