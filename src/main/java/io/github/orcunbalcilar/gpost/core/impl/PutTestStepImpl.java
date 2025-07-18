package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.*;
import java.util.function.Consumer;

/**
 * Java implementation of PutTestStep.
 */
public class PutTestStepImpl extends HttpTestStepImpl implements PutTestStep {
    
    public PutTestStepImpl(TestCaseRunContext context) {
        super(context);
    }
    
    @Override
    protected HttpRequestImpl createHttpRequest() {
        return new HttpRequestWithBodyImpl(context);
    }
    
    @Override
    public String getMethod() {
        return "PUT";
    }
    
    @Override
    public PutTestStep url(String url) {
        super.url(url);
        return this;
    }
    
    @Override
    public PutTestStep name(String name) {
        super.name(name);
        return this;
    }
    
    @Override
    public PutTestStep requestWithBody(Consumer<HttpRequestWithBody> config) {
        config.accept((HttpRequestWithBodyImpl) request);
        return this;
    }
    
    // Default implementation for request method
    @Override
    public PutTestStep request(Consumer<HttpRequest> config) {
        config.accept(request);
        return this;
    }
    
    @Override
    public PutTestStep assertions(Consumer<HttpAssertions> config) {
        super.assertions(config);
        return this;
    }
}