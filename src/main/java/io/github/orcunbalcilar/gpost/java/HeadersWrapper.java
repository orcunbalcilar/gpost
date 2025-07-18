package io.github.orcunbalcilar.gpost.java;

import java.lang.reflect.Method;

/**
 * Java wrapper for the Groovy HeadersClosure class.
 */
public class HeadersWrapper {
    
    private final Object groovyHeaders;
    
    public HeadersWrapper(Object groovyHeaders) {
        this.groovyHeaders = groovyHeaders;
    }
    
    /**
     * Add a header to the request.
     * 
     * @param name The header name
     * @param value The header value
     * @return This wrapper for method chaining
     */
    public HeadersWrapper header(String name, String value) {
        try {
            // Call the header method dynamically
            Method callMethod = groovyHeaders.getClass().getMethod("call", String.class, String.class);
            callMethod.invoke(groovyHeaders, name, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add header: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Add a Content-Type header.
     * 
     * @param contentType The content type
     * @return This wrapper for method chaining
     */
    public HeadersWrapper contentType(String contentType) {
        return header("Content-Type", contentType);
    }
    
    /**
     * Add an Accept header.
     * 
     * @param accept The accept type
     * @return This wrapper for method chaining
     */
    public HeadersWrapper accept(String accept) {
        return header("Accept", accept);
    }
    
    /**
     * Add an Authorization header.
     * 
     * @param authorization The authorization value
     * @return This wrapper for method chaining
     */
    public HeadersWrapper authorization(String authorization) {
        return header("Authorization", authorization);
    }
}