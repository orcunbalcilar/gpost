package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for request body builder.
 * Provides methods to build different types of request bodies.
 */
public interface RequestBodyBuilder {
    
    /**
     * Create a JSON request body.
     * 
     * @param jsonContent The JSON content
     * @return The request body
     */
    RequestBody json(String jsonContent);
    
    /**
     * Create an XML request body.
     * 
     * @param xmlContent The XML content
     * @return The request body
     */
    RequestBody xml(String xmlContent);
    
    /**
     * Create a plain text request body.
     * 
     * @param textContent The text content
     * @return The request body
     */
    RequestBody text(String textContent);
    
    /**
     * Create a form URL encoded request body.
     * 
     * @param formContent The form content
     * @return The request body
     */
    RequestBody form(String formContent);
    
    /**
     * Create a custom request body with specific content type.
     * 
     * @param contentType The content type
     * @param content The content
     * @return The request body
     */
    RequestBody custom(String contentType, String content);
}