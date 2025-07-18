package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for GET test steps.
 */
public interface GetTestStep extends HttpTestStep {
    
    /**
     * Set the URL for this GET request.
     * 
     * @param url The URL
     * @return This step for method chaining
     */
    @Override
    GetTestStep url(String url);
    
    /**
     * Set the name for this GET request.
     * 
     * @param name The name
     * @return This step for method chaining
     */
    @Override
    GetTestStep name(String name);
    
    /**
     * Configure the request.
     * 
     * @param config The request configuration
     * @return This step for method chaining
     */
    @Override
    GetTestStep request(Consumer<HttpRequest> config);
    
    /**
     * Configure the assertions.
     * 
     * @param config The assertions configuration
     * @return This step for method chaining
     */
    @Override
    GetTestStep assertions(Consumer<HttpAssertions> config);
}