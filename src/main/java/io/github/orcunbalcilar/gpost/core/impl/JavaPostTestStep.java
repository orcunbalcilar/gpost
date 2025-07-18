package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.PostTestStep;
import io.github.orcunbalcilar.gpost.core.HttpAssertions;
import io.github.orcunbalcilar.gpost.core.HttpRequest;
import io.github.orcunbalcilar.gpost.core.HttpRequestWithBody;
import java.util.function.Consumer;

/**
 * Java implementation of PostTestStep.
 */
public class JavaPostTestStep extends JavaHttpTestStep implements PostTestStep {
    
    public JavaPostTestStep(JavaTestCaseSpec.TestCaseRunContext context) {
        super(context);
    }
    
    @Override
    protected JavaHttpRequest createHttpRequest() {
        return new JavaHttpRequestWithBody();
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
        config.accept((JavaHttpRequestWithBody) request);
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