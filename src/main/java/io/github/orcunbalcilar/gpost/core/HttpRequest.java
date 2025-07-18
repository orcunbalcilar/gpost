package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for HTTP requests.
 */
public interface HttpRequest {
    
    /**
     * Configure the headers for this request.
     * 
     * @param config The headers configuration
     * @return This request for method chaining
     */
    HttpRequest headers(Consumer<HttpHeaders> config);
    
    /**
     * Set a header value.
     * 
     * @param name The header name
     * @param value The header value
     * @return This request for method chaining
     */
    HttpRequest header(String name, String value);
    
    /**
     * Set basic authentication.
     * 
     * @param username The username
     * @param password The password
     * @return This request for method chaining
     */
    HttpRequest basicAuth(String username, String password);
    
    /**
     * Set bearer token authentication.
     * 
     * @param token The bearer token
     * @return This request for method chaining
     */
    HttpRequest bearerAuth(String token);
}