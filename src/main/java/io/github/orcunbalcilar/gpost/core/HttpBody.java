package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for HTTP request body.
 */
public interface HttpBody {
    
    /**
     * Set the body as JSON.
     * 
     * @param json The JSON string
     * @return This body for method chaining
     */
    HttpBody json(String json);
    
    /**
     * Set the body as XML.
     * 
     * @param xml The XML string
     * @return This body for method chaining
     */
    HttpBody xml(String xml);
    
    /**
     * Set the body as plain text.
     * 
     * @param text The plain text
     * @return This body for method chaining
     */
    HttpBody text(String text);
    
    /**
     * Configure SOAP body.
     * 
     * @param config The SOAP configuration
     * @return This body for method chaining
     */
    HttpBody soap(Consumer<SoapBody> config);
}