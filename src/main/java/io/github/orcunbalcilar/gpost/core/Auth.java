package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for authentication.
 */
public interface Auth {
    
    /**
     * Get the authentication type.
     * 
     * @return The authentication type
     */
    String getType();
    
    /**
     * Apply authentication to the request.
     * This method is called by the HTTP client to apply authentication.
     * 
     * @param request The HTTP request to apply authentication to
     */
    void apply(Object request);
}