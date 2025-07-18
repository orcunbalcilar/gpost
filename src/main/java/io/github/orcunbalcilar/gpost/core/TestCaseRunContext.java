package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for test case run context.
 * Provides access to test case execution context and properties.
 */
public interface TestCaseRunContext {
    
    /**
     * Set a property in the context.
     * 
     * @param key The property key
     * @param value The property value
     */
    void setProperty(String key, Object value);
    
    /**
     * Get a property from the context.
     * 
     * @param key The property key
     * @return The property value or null if not found
     */
    Object getProperty(String key);
    
    /**
     * Get a property from the context with type casting.
     * 
     * @param key The property key
     * @param type The expected type
     * @param <T> The type parameter
     * @return The property value cast to the expected type
     */
    <T> T getProperty(String key, Class<T> type);
    
    /**
     * Check if a property exists in the context.
     * 
     * @param key The property key
     * @return true if the property exists, false otherwise
     */
    boolean hasProperty(String key);
    
    /**
     * Remove a property from the context.
     * 
     * @param key The property key
     * @return The removed property value or null if not found
     */
    Object removeProperty(String key);
    
    /**
     * Clear all properties from the context.
     */
    void clearProperties();
}