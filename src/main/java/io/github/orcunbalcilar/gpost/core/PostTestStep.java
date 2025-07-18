package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for POST test steps.
 */
public interface PostTestStep extends HttpTestStep {
    
    /**
     * Set the URL for this POST request.
     * 
     * @param url The URL
     * @return This step for method chaining
     */
    @Override
    PostTestStep url(String url);
    
    /**
     * Set the name for this POST request.
     * 
     * @param name The name
     * @return This step for method chaining
     */
    @Override
    PostTestStep name(String name);
    
    /**
     * Configure the request with body support.
     * 
     * @param config The request configuration
     * @return This step for method chaining
     */
    PostTestStep requestWithBody(Consumer<HttpRequestWithBody> config);
    
    /**
     * Configure the assertions.
     * 
     * @param config The assertions configuration
     * @return This step for method chaining
     */
    @Override
    PostTestStep assertions(Consumer<HttpAssertions> config);
}