package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCaseRunContextImplTest {
    
    private TestCaseRunContext context;
    
    @BeforeEach
    void setUp() {
        context = new TestCaseRunContextImpl();
    }
    
    @Test
    void testSetAndGetProperty() {
        context.setProperty("testKey", "testValue");
        assertEquals("testValue", context.getProperty("testKey"));
    }
    
    @Test
    void testGetNonExistentProperty() {
        assertNull(context.getProperty("nonExistent"));
    }
    
    @Test
    void testGetPropertyWithType() {
        context.setProperty("stringKey", "stringValue");
        context.setProperty("intKey", 42);
        
        assertEquals("stringValue", context.getProperty("stringKey", String.class));
        assertEquals(Integer.valueOf(42), context.getProperty("intKey", Integer.class));
    }
    
    @Test
    void testGetPropertyWithTypeReturnNull() {
        assertNull(context.getProperty("nonExistent", String.class));
    }
    
    @Test
    void testHasProperty() {
        context.setProperty("existingKey", "value");
        
        assertTrue(context.hasProperty("existingKey"));
        assertFalse(context.hasProperty("nonExistentKey"));
    }
    
    @Test
    void testRemoveProperty() {
        context.setProperty("keyToRemove", "value");
        assertTrue(context.hasProperty("keyToRemove"));
        
        assertEquals("value", context.removeProperty("keyToRemove"));
        assertFalse(context.hasProperty("keyToRemove"));
    }
    
    @Test
    void testRemoveNonExistentProperty() {
        assertNull(context.removeProperty("nonExistent"));
    }
    
    @Test
    void testClearProperties() {
        context.setProperty("key1", "value1");
        context.setProperty("key2", "value2");
        
        assertTrue(context.hasProperty("key1"));
        assertTrue(context.hasProperty("key2"));
        
        context.clearProperties();
        
        assertFalse(context.hasProperty("key1"));
        assertFalse(context.hasProperty("key2"));
    }
    
    @Test
    void testOverwriteProperty() {
        context.setProperty("key", "originalValue");
        assertEquals("originalValue", context.getProperty("key"));
        
        context.setProperty("key", "newValue");
        assertEquals("newValue", context.getProperty("key"));
    }
    
    @Test
    void testNullValue() {
        context.setProperty("nullKey", null);
        assertTrue(context.hasProperty("nullKey"));
        assertNull(context.getProperty("nullKey"));
    }
}