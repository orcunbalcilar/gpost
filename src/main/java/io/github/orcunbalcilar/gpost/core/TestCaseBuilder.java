package io.github.orcunbalcilar.gpost.core;

import io.github.orcunbalcilar.gpost.core.impl.TestCaseImpl;
import io.github.orcunbalcilar.gpost.core.impl.TestCaseSpecImpl;
import java.util.function.Consumer;

/**
 * Core class for building test cases.
 * Provides fluent API for both Groovy and Java DSL implementations.
 */
public class TestCaseBuilder {
    
    /**
     * Create a new test case with the given name and configuration.
     * 
     * @param name The test case name
     * @param config The test case configuration
     * @return A TestCase object
     */
    public TestCase testCase(String name, Consumer<TestCaseSpec> config) {
        TestCaseImpl testCase = new TestCaseImpl(name);
        TestCaseSpecImpl spec = new TestCaseSpecImpl(testCase);
        config.accept(spec);
        return testCase;
    }
    
    /**
     * Create a simple GET request test case.
     * 
     * @param config The GET request configuration
     * @return A TestCase object
     */
    public TestCase get(Consumer<GetTestStep> config) {
        TestCaseImpl testCase = new TestCaseImpl("GET Test Case");
        TestCaseSpecImpl spec = new TestCaseSpecImpl(testCase);
        spec.get(config);
        return testCase;
    }
    
    /**
     * Create a simple POST request test case.
     * 
     * @param config The POST request configuration
     * @return A TestCase object
     */
    public TestCase post(Consumer<PostTestStep> config) {
        TestCaseImpl testCase = new TestCaseImpl("POST Test Case");
        TestCaseSpecImpl spec = new TestCaseSpecImpl(testCase);
        spec.post(config);
        return testCase;
    }
    
    /**
     * Create a simple PUT request test case.
     * 
     * @param config The PUT request configuration
     * @return A TestCase object
     */
    public TestCase put(Consumer<PutTestStep> config) {
        TestCaseImpl testCase = new TestCaseImpl("PUT Test Case");
        TestCaseSpecImpl spec = new TestCaseSpecImpl(testCase);
        spec.put(config);
        return testCase;
    }
}
