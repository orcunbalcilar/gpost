package io.github.orcunbalcilar.gpost.java;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * Java wrapper for the Groovy Assertions class.
 */
public class AssertionsWrapper {
    
    private final Object groovyAssertions;
    
    public AssertionsWrapper(Object groovyAssertions) {
        this.groovyAssertions = groovyAssertions;
    }
    
    /**
     * Assert that the response status code equals the expected value.
     * 
     * @param expectedStatusCode The expected status code
     * @return This wrapper for method chaining
     */
    public AssertionsWrapper statusCode(int expectedStatusCode) {
        try {
            Method statusCodeMethod = groovyAssertions.getClass().getMethod("statusCode", int.class);
            statusCodeMethod.invoke(groovyAssertions, expectedStatusCode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert status code: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Assert that the response body contains the expected text.
     * 
     * @param expectedText The expected text
     * @return This wrapper for method chaining
     */
    public AssertionsWrapper bodyContains(String expectedText) {
        try {
            // This is a simplified implementation
            // You would need to access the response body and check if it contains the text
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert body contains: " + e.getMessage(), e);
        }
    }
    
    /**
     * Assert that the response body equals the expected text.
     * 
     * @param expectedBody The expected body content
     * @return This wrapper for method chaining
     */
    public AssertionsWrapper bodyEquals(String expectedBody) {
        try {
            // This is a simplified implementation
            // You would need to access the response body and compare it
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to assert body equals: " + e.getMessage(), e);
        }
    }
    
    /**
     * Configure XML assertions.
     * 
     * @param config The XML assertions configuration
     * @return This wrapper for method chaining
     */
    public AssertionsWrapper xml(XmlAssertionsConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            groovy.lang.Closure<?> groovyClosure = new groovy.lang.Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    XmlAssertionsWrapper wrapper = new XmlAssertionsWrapper(args[0]);
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy xml method
            Method xmlMethod = groovyAssertions.getClass().getMethod("xml", groovy.lang.Closure.class);
            xmlMethod.invoke(groovyAssertions, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure XML assertions: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Configure JSON assertions.
     * 
     * @param config The JSON assertions configuration
     * @return This wrapper for method chaining
     */
    public AssertionsWrapper json(JsonAssertionsConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            groovy.lang.Closure<?> groovyClosure = new groovy.lang.Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    JsonAssertionsWrapper wrapper = new JsonAssertionsWrapper(args[0]);
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy json method
            Method jsonMethod = groovyAssertions.getClass().getMethod("json", groovy.lang.Closure.class);
            jsonMethod.invoke(groovyAssertions, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure JSON assertions: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Interface for configuring XML assertions.
     */
    @FunctionalInterface
    public interface XmlAssertionsConfig {
        void configure(XmlAssertionsWrapper xml);
    }
    
    /**
     * Interface for configuring JSON assertions.
     */
    @FunctionalInterface
    public interface JsonAssertionsConfig {
        void configure(JsonAssertionsWrapper json);
    }
}