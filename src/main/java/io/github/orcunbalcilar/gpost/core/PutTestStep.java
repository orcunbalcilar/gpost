package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for PUT test steps.
 */
public interface PutTestStep extends HttpTestStep {
    
    /**
     * Set the URL for this PUT request.
     * 
     * @param url The URL
     * @return This step for method chaining
     */
    @Override
    PutTestStep url(String url);
    
    /**
     * Set the name for this PUT request.
     * 
     * @param name The name
     * @return This step for method chaining
     */
    @Override
    PutTestStep name(String name);
    
    /**
     * Configure the request with body support.
     * 
     * @param config The request configuration
     * @return This step for method chaining
     */
    PutTestStep requestWithBody(Consumer<HttpRequestWithBody> config);
    
    /**
     * Configure the assertions.
     * 
     * @param config The assertions configuration
     * @return This step for method chaining
     */
    @Override
    PutTestStep assertions(Consumer<HttpAssertions> config);
}