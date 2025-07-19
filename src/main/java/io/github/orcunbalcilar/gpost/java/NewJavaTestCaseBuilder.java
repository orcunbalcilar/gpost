package io.github.orcunbalcilar.gpost.java;

import io.github.orcunbalcilar.gpost.core.*;
import java.util.function.Consumer;

/**
 * Reflection-free Java DSL for creating test cases.
 * This implementation eliminates all reflection usage from the previous version.
 */
public class NewJavaTestCaseBuilder {
    
    private final TestCaseBuilder coreBuilder;
    
    public NewJavaTestCaseBuilder() {
        this.coreBuilder = new TestCaseBuilder();
    }
    
    /**
     * Create a new test case with the given name and configuration.
     * 
     * @param name The test case name
     * @param config The test case configuration
     * @return A TestCase object
     */
    public TestCase testCase(String name, Consumer<TestCaseSpec> config) {
        return coreBuilder.testCase(name, config);
    }
    
    /**
     * Create a simple GET request test case.
     * 
     * @param config The GET request configuration
     * @return A TestCase object
     */
    public TestCase get(Consumer<GetTestStep> config) {
        return coreBuilder.get(config);
    }
    
    /**
     * Create a simple POST request test case.
     * 
     * @param config The POST request configuration
     * @return A TestCase object
     */
    public TestCase post(Consumer<PostTestStep> config) {
        return coreBuilder.post(config);
    }
    
    /**
     * Create a simple PUT request test case.
     * 
     * @param config The PUT request configuration
     * @return A TestCase object
     */
    public TestCase put(Consumer<PutTestStep> config) {
        return coreBuilder.put(config);
    }
}