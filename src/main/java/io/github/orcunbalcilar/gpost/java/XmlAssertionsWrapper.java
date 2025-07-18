package io.github.orcunbalcilar.gpost.java;

import java.lang.reflect.Method;

/**
 * Java wrapper for XML assertions.
 */
public class XmlAssertionsWrapper {
    
    private final Object groovyXmlAssertions;
    
    public XmlAssertionsWrapper(Object groovyXmlAssertions) {
        this.groovyXmlAssertions = groovyXmlAssertions;
    }
    
    /**
     * Assert that an XML element equals the expected value.
     * 
     * @param actualValue The actual value to compare
     * @param expectedValue The expected value
     * @return This wrapper for method chaining
     */
    public XmlAssertionsWrapper equals(String actualValue, String expectedValue) {
        try {
            Method equalsMethod = groovyXmlAssertions.getClass().getMethod("equals", String.class, String.class);
            equalsMethod.invoke(groovyXmlAssertions, actualValue, expectedValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert XML equals: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Assert that an XML element contains the expected value.
     * 
     * @param actualValue The actual value to check
     * @param expectedValue The expected value that should be contained
     * @return This wrapper for method chaining
     */
    public XmlAssertionsWrapper contains(String actualValue, String expectedValue) {
        try {
            // This is a simplified implementation
            if (!actualValue.contains(expectedValue)) {
                throw new AssertionError("Expected '" + actualValue + "' to contain '" + expectedValue + "'");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert XML contains: " + e.getMessage(), e);
        }
        return this;
    }
}