package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for response body assertions.
 * Provides methods to assert on response body content.
 */
public interface ResponseBodyAssertions {
    
    /**
     * Assert that the response body equals the expected value.
     * 
     * @param expected The expected value
     * @return This assertions for method chaining
     */
    ResponseBodyAssertions equalsTo(Object expected);
    
    /**
     * Assert that the response body contains the expected text.
     * 
     * @param text The expected text
     * @return This assertions for method chaining
     */
    ResponseBodyAssertions contains(String text);
    
    /**
     * Assert that the response body starts with the expected text.
     * 
     * @param text The expected text
     * @return This assertions for method chaining
     */
    ResponseBodyAssertions startsWith(String text);
    
    /**
     * Assert that the response body ends with the expected text.
     * 
     * @param text The expected text
     * @return This assertions for method chaining
     */
    ResponseBodyAssertions endsWith(String text);
    
    /**
     * Assert that the response body matches the expected pattern.
     * 
     * @param pattern The expected pattern
     * @return This assertions for method chaining
     */
    ResponseBodyAssertions matches(String pattern);
    
    /**
     * Assert that the response body size equals the expected size.
     * 
     * @param expectedSize The expected size
     * @return This assertions for method chaining
     */
    ResponseBodyAssertions size(int expectedSize);
    
    /**
     * Assert that the response body is empty.
     * 
     * @return This assertions for method chaining
     */
    ResponseBodyAssertions isEmpty();
    
    /**
     * Assert that the response body is not empty.
     * 
     * @return This assertions for method chaining
     */
    ResponseBodyAssertions isNotEmpty();
    
    /**
     * Get the actual response body for custom assertions.
     * 
     * @return The actual response body
     */
    Object getActual();
}