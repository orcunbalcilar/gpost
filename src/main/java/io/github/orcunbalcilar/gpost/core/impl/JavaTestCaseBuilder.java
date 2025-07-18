package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.*;
import java.util.function.Consumer;

/**
 * Java implementation of TestCaseBuilder.
 */
public class JavaTestCaseBuilder implements TestCaseBuilder {
    
    @Override
    public TestCase testCase(String name, Consumer<TestCaseSpec> config) {
        JavaTestCase testCase = new JavaTestCase(name);
        JavaTestCaseSpec spec = new JavaTestCaseSpec(testCase);
        config.accept(spec);
        return testCase;
    }
    
    @Override
    public TestCase get(Consumer<GetTestStep> config) {
        JavaTestCase testCase = new JavaTestCase();
        JavaTestCaseSpec spec = new JavaTestCaseSpec(testCase);
        spec.get(config);
        return testCase;
    }
    
    @Override
    public TestCase post(Consumer<PostTestStep> config) {
        JavaTestCase testCase = new JavaTestCase();
        JavaTestCaseSpec spec = new JavaTestCaseSpec(testCase);
        spec.post(config);
        return testCase;
    }
    
    @Override
    public TestCase put(Consumer<PutTestStep> config) {
        JavaTestCase testCase = new JavaTestCase();
        JavaTestCaseSpec spec = new JavaTestCaseSpec(testCase);
        spec.put(config);
        return testCase;
    }
}