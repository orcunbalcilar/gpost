package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for JSON response assertions.
 */
public interface JsonAssertions {
    
    /**
     * Assert that a JSON path exists.
     * 
     * @param path The JSON path
     * @return This assertions for method chaining
     */
    JsonAssertions pathExists(String path);
    
    /**
     * Assert that a JSON path has a specific value.
     * 
     * @param path The JSON path
     * @param expectedValue The expected value
     * @return This assertions for method chaining
     */
    JsonAssertions pathEquals(String path, Object expectedValue);
    
    /**
     * Assert that a JSON path contains a specific value.
     * 
     * @param path The JSON path
     * @param expectedValue The expected value
     * @return This assertions for method chaining
     */
    JsonAssertions pathContains(String path, String expectedValue);
    
    /**
     * Assert that a JSON array has a specific size.
     * 
     * @param path The JSON path to the array
     * @param expectedSize The expected size
     * @return This assertions for method chaining
     */
    JsonAssertions arraySize(String path, int expectedSize);
}