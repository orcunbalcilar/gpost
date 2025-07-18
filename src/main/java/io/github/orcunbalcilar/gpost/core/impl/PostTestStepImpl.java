package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.*;
import java.util.function.Consumer;

/**
 * Java implementation of PostTestStep.
 */
public class PostTestStepImpl extends HttpTestStepImpl implements PostTestStep {
    
    public PostTestStepImpl(TestCaseRunContext context) {
        super(context);
    }
    
    @Override
    protected HttpRequestImpl createHttpRequest() {
        return new HttpRequestWithBodyImpl(context);
    }
    
    @Override
    public String getMethod() {
        return "POST";
    }
    
    @Override
    public PostTestStep url(String url) {
        super.url(url);
        return this;
    }
    
    @Override
    public PostTestStep name(String name) {
        super.name(name);
        return this;
    }
    
    @Override
    public PostTestStep requestWithBody(Consumer<HttpRequestWithBody> config) {
        config.accept((HttpRequestWithBodyImpl) request);
        return this;
    }
    
    // Default implementation for request method
    @Override
    public PostTestStep request(Consumer<HttpRequest> config) {
        config.accept(request);
        return this;
    }
    
    @Override
    public PostTestStep assertions(Consumer<HttpAssertions> config) {
        super.assertions(config);
        return this;
    }
}