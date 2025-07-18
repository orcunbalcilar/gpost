package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for HTTP test steps.
 * Both Groovy and Java DSL implementations should implement this interface.
 */
public interface HttpTestStep extends TestStep {
    
    /**
     * Set the URL for this HTTP request.
     * 
     * @param url The URL
     * @return This step for method chaining
     */
    HttpTestStep url(String url);
    
    /**
     * Set the name for this HTTP request.
     * 
     * @param name The name
     * @return This step for method chaining
     */
    HttpTestStep name(String name);
    
    /**
     * Configure the request.
     * 
     * @param config The request configuration
     * @return This step for method chaining
     */
    HttpTestStep request(Consumer<HttpRequest> config);
    
    /**
     * Configure the assertions.
     * 
     * @param config The assertions configuration
     * @return This step for method chaining
     */
    HttpTestStep assertions(Consumer<HttpAssertions> config);
    
    /**
     * Get the HTTP method for this request.
     * 
     * @return The HTTP method
     */
    String getMethod();
    
    /**
     * Get the URL for this request.
     * 
     * @return The URL
     */
    String getUrl();
}