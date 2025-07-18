package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for basic authentication.
 */
public interface BasicAuth extends Auth {
    
    /**
     * Set the username for basic authentication.
     * 
     * @param username The username
     * @return This basic auth for method chaining
     */
    BasicAuth username(String username);
    
    /**
     * Set the password for basic authentication.
     * 
     * @param password The password
     * @return This basic auth for method chaining
     */
    BasicAuth password(String password);
    
    /**
     * Get the username.
     * 
     * @return The username
     */
    String getUsername();
    
    /**
     * Get the password.
     * 
     * @return The password
     */
    String getPassword();
}