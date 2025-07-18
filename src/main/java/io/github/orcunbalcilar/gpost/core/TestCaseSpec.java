package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for test case specification.
 * Both Groovy and Java DSL implementations should implement this interface.
 */
public interface TestCaseSpec extends HasAuth, ContextAccess {
    
    /**
     * Add a GET request test step.
     * 
     * @param config The GET request configuration
     * @return This spec for method chaining
     */
    TestCaseSpec get(Consumer<GetTestStep> config);
    
    /**
     * Add a POST request test step.
     * 
     * @param config The POST request configuration
     * @return This spec for method chaining
     */
    TestCaseSpec post(Consumer<PostTestStep> config);
    
    /**
     * Add a PUT request test step.
     * 
     * @param config The PUT request configuration
     * @return This spec for method chaining
     */
    TestCaseSpec put(Consumer<PutTestStep> config);
    
    /**
     * Add a script test step.
     * 
     * @param name The script name
     * @param script The script to run
     * @return This spec for method chaining
     */
    TestCaseSpec script(String name, Runnable script);
    
    /**
     * Get the underlying test case.
     * 
     * @return The test case
     */
    TestCase getTestCase();
}