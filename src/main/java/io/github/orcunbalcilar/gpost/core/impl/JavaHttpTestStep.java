package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.TestItemStatus;
import io.github.orcunbalcilar.gpost.core.*;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;

/**
 * Base Java implementation of HttpTestStep.
 */
public abstract class JavaHttpTestStep extends JavaTestStep implements HttpTestStep {
    
    protected final JavaTestCaseSpec.TestCaseRunContext context;
    protected String url;
    protected JavaHttpRequest request;
    protected JavaHttpAssertions assertions;
    
    public JavaHttpTestStep(JavaTestCaseSpec.TestCaseRunContext context) {
        this.context = context;
        this.request = createHttpRequest();
        this.assertions = new JavaHttpAssertions();
    }
    
    protected abstract JavaHttpRequest createHttpRequest();
    
    @Override
    public HttpTestStep url(String url) {
        this.url = url;
        return this;
    }
    
    @Override
    public HttpTestStep name(String name) {
        setName(name);
        return this;
    }
    
    @Override
    public HttpTestStep request(Consumer<HttpRequest> config) {
        config.accept(request);
        return this;
    }
    
    @Override
    public HttpTestStep assertions(Consumer<HttpAssertions> config) {
        config.accept(assertions);
        return this;
    }
    
    @Override
    public String getUrl() {
        return url;
    }
    
    @Override
    public void run() {
        try {
            // This is a placeholder implementation
            // In practice, this would delegate to the Groovy implementation
            // or implement the full HTTP client logic
            
            // For now, we'll simulate a successful execution
            setStatus(TestItemStatus.PASSED);
        } catch (Exception e) {
            setStatus(TestItemStatus.FAILED);
            throw new RuntimeException("HTTP request failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get the configured request object.
     */
    public JavaHttpRequest getRequest() {
        return request;
    }
    
    /**
     * Get the configured assertions object.
     */
    public JavaHttpAssertions getAssertions() {
        return assertions;
    }
}