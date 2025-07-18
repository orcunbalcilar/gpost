package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for HTTP requests with body (POST, PUT, etc.).
 */
public interface HttpRequestWithBody extends HttpRequest {
    
    /**
     * Configure the body for this request.
     * 
     * @param config The body configuration
     * @return This request for method chaining
     */
    HttpRequestWithBody body(Consumer<HttpBody> config);
    
    /**
     * Set the request body as JSON.
     * 
     * @param json The JSON string
     * @return This request for method chaining
     */
    HttpRequestWithBody jsonBody(String json);
    
    /**
     * Set the request body as XML.
     * 
     * @param xml The XML string
     * @return This request for method chaining
     */
    HttpRequestWithBody xmlBody(String xml);
    
    /**
     * Set the request body as plain text.
     * 
     * @param text The plain text
     * @return This request for method chaining
     */
    HttpRequestWithBody textBody(String text);
}