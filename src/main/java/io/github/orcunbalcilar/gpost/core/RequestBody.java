package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for request body.
 * Provides access to request body content and type.
 */
public interface RequestBody extends ContextAccess {
    
    /**
     * Get the content type of the request body.
     * 
     * @return The content type
     */
    String getContentType();
    
    /**
     * Build the request body content.
     * 
     * @return The request body content as string
     */
    String build();
    
    /**
     * Get the raw body content.
     * 
     * @return The raw body content
     */
    String getContent();
    
    /**
     * Set the raw body content.
     * 
     * @param content The body content
     */
    void setContent(String content);
}