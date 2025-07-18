package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for HTTP headers.
 */
public interface HttpHeaders {
    
    /**
     * Set the Accept header.
     * 
     * @param accept The Accept header value
     * @return This headers for method chaining
     */
    HttpHeaders accept(String accept);
    
    /**
     * Set the Content-Type header.
     * 
     * @param contentType The Content-Type header value
     * @return This headers for method chaining
     */
    HttpHeaders contentType(String contentType);
    
    /**
     * Set the Authorization header.
     * 
     * @param authorization The Authorization header value
     * @return This headers for method chaining
     */
    HttpHeaders authorization(String authorization);
    
    /**
     * Set a custom header.
     * 
     * @param name The header name
     * @param value The header value
     * @return This headers for method chaining
     */
    HttpHeaders header(String name, String value);
}