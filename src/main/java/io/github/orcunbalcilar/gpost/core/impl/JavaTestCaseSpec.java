package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.*;
import java.util.function.Consumer;

/**
 * Java implementation of TestCaseSpec.
 */
public class JavaTestCaseSpec implements TestCaseSpec {
    
    private final TestCase testCase;
    private final TestCaseRunContext context;
    
    public JavaTestCaseSpec(TestCase testCase) {
        this.testCase = testCase;
        this.context = new TestCaseRunContext();
    }
    
    @Override
    public TestCaseSpec get(Consumer<GetTestStep> config) {
        JavaGetTestStep getTestStep = new JavaGetTestStep(context);
        config.accept(getTestStep);
        testCase.addTestStep(getTestStep);
        return this;
    }
    
    @Override
    public TestCaseSpec post(Consumer<PostTestStep> config) {
        JavaPostTestStep postTestStep = new JavaPostTestStep(context);
        config.accept(postTestStep);
        testCase.addTestStep(postTestStep);
        return this;
    }
    
    @Override
    public TestCaseSpec put(Consumer<PutTestStep> config) {
        JavaPutTestStep putTestStep = new JavaPutTestStep(context);
        config.accept(putTestStep);
        testCase.addTestStep(putTestStep);
        return this;
    }
    
    @Override
    public TestCaseSpec script(String name, Runnable script) {
        JavaScriptTestStep scriptTestStep = new JavaScriptTestStep(name, script);
        testCase.addTestStep(scriptTestStep);
        return this;
    }
    
    @Override
    public TestCase getTestCase() {
        return testCase;
    }
    
    /**
     * Simple context holder for test case execution.
     */
    public static class TestCaseRunContext {
        // This is a simple placeholder for the context
        // In a real implementation, this would hold execution state
    }
}