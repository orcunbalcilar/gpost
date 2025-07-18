package io.github.orcunbalcilar.gpost.java;

import groovy.lang.Closure;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Java wrapper for the Groovy Request class.
 */
public class RequestWrapper {
    
    private final Object groovyRequest;
    
    public RequestWrapper(Object groovyRequest) {
        this.groovyRequest = groovyRequest;
    }
    
    /**
     * Configure headers for the request.
     * 
     * @param headers A map of header names to values
     * @return This wrapper for method chaining
     */
    public RequestWrapper headers(Map<String, String> headers) {
        try {
            // Create a Groovy closure that sets headers
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    try {
                        Object headersObject = args[0];
                        for (Map.Entry<String, String> entry : headers.entrySet()) {
                            // Call the header method dynamically
                            Method headerMethod = headersObject.getClass().getMethod("call", String.class, String.class);
                            headerMethod.invoke(headersObject, entry.getKey(), entry.getValue());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to set headers: " + e.getMessage(), e);
                    }
                    return null;
                }
            };
            
            // Call the Groovy headers method
            Method headersMethod = groovyRequest.getClass().getMethod("headers", Closure.class);
            headersMethod.invoke(groovyRequest, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure headers: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Configure headers for the request using a lambda.
     * 
     * @param config The headers configuration
     * @return This wrapper for method chaining
     */
    public RequestWrapper headers(HeadersConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    HeadersWrapper wrapper = new HeadersWrapper(args[0]);
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy headers method
            Method headersMethod = groovyRequest.getClass().getMethod("headers", Closure.class);
            headersMethod.invoke(groovyRequest, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure headers: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Configure query parameters for the request.
     * 
     * @param params A map of parameter names to values
     * @return This wrapper for method chaining
     */
    public RequestWrapper params(Map<String, String> params) {
        try {
            // For GET requests, we need to handle parameters differently
            // This is a simplified implementation
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure parameters: " + e.getMessage(), e);
        }
    }
    
    /**
     * Interface for configuring headers.
     */
    @FunctionalInterface
    public interface HeadersConfig {
        void configure(HeadersWrapper headers);
    }
}