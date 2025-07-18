package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.*;
import java.util.function.Consumer;

/**
 * Java implementation of TestCaseSpec.
 */
public class TestCaseSpecImpl implements TestCaseSpec {
    
    private final TestCase testCase;
    private final TestCaseRunContext context;
    private Auth auth;
    
    public TestCaseSpecImpl(TestCase testCase) {
        this.testCase = testCase;
        this.context = new TestCaseRunContextImpl();
    }
    
    @Override
    public TestCaseSpec get(Consumer<GetTestStep> config) {
        GetTestStepImpl getTestStep = new GetTestStepImpl(context);
        config.accept(getTestStep);
        testCase.addTestStep(getTestStep);
        return this;
    }
    
    @Override
    public TestCaseSpec post(Consumer<PostTestStep> config) {
        PostTestStepImpl postTestStep = new PostTestStepImpl(context);
        config.accept(postTestStep);
        testCase.addTestStep(postTestStep);
        return this;
    }
    
    @Override
    public TestCaseSpec put(Consumer<PutTestStep> config) {
        PutTestStepImpl putTestStep = new PutTestStepImpl(context);
        config.accept(putTestStep);
        testCase.addTestStep(putTestStep);
        return this;
    }
    
    @Override
    public TestCaseSpec script(String name, Runnable script) {
        ScriptTestStepImpl scriptTestStep = new ScriptTestStepImpl(name, script);
        testCase.addTestStep(scriptTestStep);
        return this;
    }
    
    @Override
    public HasAuth basicAuth(Consumer<BasicAuth> config) {
        BasicAuthImpl basicAuth = new BasicAuthImpl();
        config.accept(basicAuth);
        this.auth = basicAuth;
        return this;
    }
    
    @Override
    public Auth getAuth() {
        return auth;
    }
    
    @Override
    public TestCaseRunContext getContext() {
        return context;
    }
    
    @Override
    public TestCase getTestCase() {
        return testCase;
    }
}