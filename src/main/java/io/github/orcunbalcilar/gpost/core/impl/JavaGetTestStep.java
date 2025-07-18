package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.GetTestStep;
import io.github.orcunbalcilar.gpost.core.HttpAssertions;
import io.github.orcunbalcilar.gpost.core.HttpRequest;
import java.util.function.Consumer;

/**
 * Java implementation of GetTestStep.
 */
public class JavaGetTestStep extends JavaHttpTestStep implements GetTestStep {
    
    public JavaGetTestStep(JavaTestCaseSpec.TestCaseRunContext context) {
        super(context);
    }
    
    @Override
    protected JavaHttpRequest createHttpRequest() {
        return new JavaHttpRequest();
    }
    
    @Override
    public String getMethod() {
        return "GET";
    }
    
    @Override
    public GetTestStep url(String url) {
        super.url(url);
        return this;
    }
    
    @Override
    public GetTestStep name(String name) {
        super.name(name);
        return this;
    }
    
    @Override
    public GetTestStep request(Consumer<HttpRequest> config) {
        super.request(config);
        return this;
    }
    
    @Override
    public GetTestStep assertions(Consumer<HttpAssertions> config) {
        super.assertions(config);
        return this;
    }
}