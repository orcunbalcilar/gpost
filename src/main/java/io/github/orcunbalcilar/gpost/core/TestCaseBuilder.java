package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for building test cases.
 * Both Groovy and Java DSL implementations should implement this interface.
 */
public interface TestCaseBuilder {
    
    /**
     * Create a new test case with the given name and configuration.
     * 
     * @param name The test case name
     * @param config The test case configuration
     * @return A TestCase object
     */
    TestCase testCase(String name, Consumer<TestCaseSpec> config);
    
    /**
     * Create a simple GET request test case.
     * 
     * @param config The GET request configuration
     * @return A TestCase object
     */
    TestCase get(Consumer<GetTestStep> config);
    
    /**
     * Create a simple POST request test case.
     * 
     * @param config The POST request configuration
     * @return A TestCase object
     */
    TestCase post(Consumer<PostTestStep> config);
    
    /**
     * Create a simple PUT request test case.
     * 
     * @param config The PUT request configuration
     * @return A TestCase object
     */
    TestCase put(Consumer<PutTestStep> config);
}