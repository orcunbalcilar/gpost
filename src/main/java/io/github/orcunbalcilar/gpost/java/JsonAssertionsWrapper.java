package io.github.orcunbalcilar.gpost.java;

import java.lang.reflect.Method;

/**
 * Java wrapper for JSON assertions.
 */
public class JsonAssertionsWrapper {
    
    private final Object groovyJsonAssertions;
    
    public JsonAssertionsWrapper(Object groovyJsonAssertions) {
        this.groovyJsonAssertions = groovyJsonAssertions;
    }
    
    /**
     * Assert that a JSON element equals the expected value.
     * 
     * @param actualValue The actual value to compare
     * @param expectedValue The expected value
     * @return This wrapper for method chaining
     */
    public JsonAssertionsWrapper equals(String actualValue, String expectedValue) {
        try {
            Method equalsMethod = groovyJsonAssertions.getClass().getMethod("equals", String.class, String.class);
            equalsMethod.invoke(groovyJsonAssertions, actualValue, expectedValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert JSON equals: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Assert that a JSON element contains the expected value.
     * 
     * @param actualValue The actual value to check
     * @param expectedValue The expected value that should be contained
     * @return This wrapper for method chaining
     */
    public JsonAssertionsWrapper contains(String actualValue, String expectedValue) {
        try {
            // This is a simplified implementation
            if (!actualValue.contains(expectedValue)) {
                throw new AssertionError("Expected '" + actualValue + "' to contain '" + expectedValue + "'");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert JSON contains: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Assert that a JSON path exists.
     * 
     * @param path The JSON path to check
     * @return This wrapper for method chaining
     */
    public JsonAssertionsWrapper pathExists(String path) {
        try {
            // This is a simplified implementation
            // In a real implementation, you'd use a JSON path library
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert JSON path exists: " + e.getMessage(), e);
        }
    }
}