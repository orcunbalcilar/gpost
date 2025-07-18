package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.*;
import java.util.function.Consumer;

/**
 * Java implementation of TestCaseBuilder.
 */
public class TestCaseBuilderImpl implements TestCaseBuilder {
    
    @Override
    public TestCase testCase(String name, Consumer<TestCaseSpec> config) {
        TestCaseImpl testCase = new TestCaseImpl(name);
        TestCaseSpecImpl spec = new TestCaseSpecImpl(testCase);
        config.accept(spec);
        return testCase;
    }
    
    @Override
    public TestCase get(Consumer<GetTestStep> config) {
        TestCaseImpl testCase = new TestCaseImpl();
        TestCaseSpecImpl spec = new TestCaseSpecImpl(testCase);
        spec.get(config);
        return testCase;
    }
    
    @Override
    public TestCase post(Consumer<PostTestStep> config) {
        TestCaseImpl testCase = new TestCaseImpl();
        TestCaseSpecImpl spec = new TestCaseSpecImpl(testCase);
        spec.post(config);
        return testCase;
    }
    
    @Override
    public TestCase put(Consumer<PutTestStep> config) {
        TestCaseImpl testCase = new TestCaseImpl();
        TestCaseSpecImpl spec = new TestCaseSpecImpl(testCase);
        spec.put(config);
        return testCase;
    }
}