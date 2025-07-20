package io.github.orcunbalcilar.gpost.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TestCaseRunContextImpl.
 * Tests the property management functionality that is crucial for test execution context.
 */
class TestCaseRunContextImplTest {

    private TestCaseRunContextImpl context;

    @BeforeEach
    void setUp() {
        context = new TestCaseRunContextImpl();
    }

    @Test
    void shouldSetAndGetStringProperty() {
        // Given
        String key = "testKey";
        String value = "testValue";

        // When
        context.setProperty(key, value);

        // Then
        assertEquals(value, context.getProperty(key));
    }

    @Test
    void shouldSetAndGetIntegerProperty() {
        // Given
        String key = "intKey";
        Integer value = 42;

        // When
        context.setProperty(key, value);

        // Then
        assertEquals(value, context.getProperty(key));
    }

    @Test
    void shouldSetAndGetBooleanProperty() {
        // Given
        String key = "boolKey";
        Boolean value = true;

        // When
        context.setProperty(key, value);

        // Then
        assertEquals(value, context.getProperty(key));
    }

    @Test
    void shouldOverwriteExistingProperty() {
        // Given
        String key = "overwriteKey";
        String originalValue = "originalValue";
        String newValue = "newValue";

        // When
        context.setProperty(key, originalValue);
        context.setProperty(key, newValue);

        // Then
        assertEquals(newValue, context.getProperty(key));
    }

    @Test
    void shouldReturnNullForNonExistentProperty() {
        // Given
        String nonExistentKey = "nonExistent";

        // When
        Object result = context.getProperty(nonExistentKey);

        // Then
        assertNull(result);
    }

    @Test
    void shouldHandleNullKey() {
        // When & Then
        assertDoesNotThrow(() -> {
            context.setProperty(null, "value");
            context.getProperty(null);
        });
    }

    @Test
    void shouldHandleNullValue() {
        // Given
        String key = "nullValueKey";

        // When
        context.setProperty(key, null);

        // Then
        assertNull(context.getProperty(key));
    }

    @Test
    void shouldHandleComplexObjects() {
        // Given
        String key = "complexObject";
        java.util.Map<String, String> complexValue = java.util.Map.of("nested", "value");

        // When
        context.setProperty(key, complexValue);

        // Then
        assertEquals(complexValue, context.getProperty(key));
    }

    @Test
    void shouldMaintainMultipleProperties() {
        // Given
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";
        String value1 = "value1";
        Integer value2 = 123;
        Boolean value3 = false;

        // When
        context.setProperty(key1, value1);
        context.setProperty(key2, value2);
        context.setProperty(key3, value3);

        // Then
        assertEquals(value1, context.getProperty(key1));
        assertEquals(value2, context.getProperty(key2));
        assertEquals(value3, context.getProperty(key3));
    }

    @Test
    void shouldHandleEmptyKey() {
        // Given
        String emptyKey = "";
        String value = "valueForEmptyKey";

        // When
        context.setProperty(emptyKey, value);

        // Then
        assertEquals(value, context.getProperty(emptyKey));
    }

    @Test
    void shouldHandleSpecialCharactersInKey() {
        // Given
        String specialKey = "key_with-special.chars@123";
        String value = "specialValue";

        // When
        context.setProperty(specialKey, value);

        // Then
        assertEquals(value, context.getProperty(specialKey));
    }

    @Test
    void shouldReturnTypedProperty() {
        // Given
        String key = "typedProperty";
        String value = "stringValue";

        // When
        context.setProperty(key, value);

        // Then
        String typedResult = context.getProperty(key, String.class);
        assertEquals(value, typedResult);
    }

    @Test
    void shouldReturnNullForTypedPropertyWithWrongType() {
        // Given
        String key = "typedProperty";
        String value = "stringValue";
        context.setProperty(key, value);

        // When & Then
        assertThrows(ClassCastException.class, () -> {
            context.getProperty(key, Integer.class);
        });
    }

    @Test
    void shouldCheckPropertyExistence() {
        // Given
        String existingKey = "existingKey";
        String nonExistingKey = "nonExistingKey";
        context.setProperty(existingKey, "value");

        // When & Then
        assertTrue(context.hasProperty(existingKey));
        assertFalse(context.hasProperty(nonExistingKey));
    }

    @Test
    void shouldRemoveProperty() {
        // Given
        String key = "keyToRemove";
        String value = "valueToRemove";
        context.setProperty(key, value);

        // When
        Object removedValue = context.removeProperty(key);

        // Then
        assertEquals(value, removedValue);
        assertFalse(context.hasProperty(key));
        assertNull(context.getProperty(key));
    }

    @Test
    void shouldReturnNullWhenRemovingNonExistentProperty() {
        // Given
        String nonExistentKey = "nonExistent";

        // When
        Object result = context.removeProperty(nonExistentKey);

        // Then
        assertNull(result);
    }

    @Test
    void shouldClearAllProperties() {
        // Given
        context.setProperty("key1", "value1");
        context.setProperty("key2", "value2");
        context.setProperty("key3", "value3");

        // When
        context.clearProperties();

        // Then
        assertFalse(context.hasProperty("key1"));
        assertFalse(context.hasProperty("key2"));
        assertFalse(context.hasProperty("key3"));
        assertNull(context.getProperty("key1"));
        assertNull(context.getProperty("key2"));
        assertNull(context.getProperty("key3"));
    }
}
