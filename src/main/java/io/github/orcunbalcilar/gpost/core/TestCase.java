package io.github.orcunbalcilar.gpost.core;

import io.github.orcunbalcilar.gpost.TestItemStatus;
import java.util.List;

/**
 * Core interface for test cases.
 * Both Groovy and Java DSL implementations should implement this interface.
 */
public interface TestCase {
    
    /**
     * Get the name of the test case.
     * 
     * @return The test case name
     */
    String getName();
    
    /**
     * Set the name of the test case.
     * 
     * @param name The test case name
     */
    void setName(String name);
    
    /**
     * Get the test steps in this test case.
     * 
     * @return List of test steps
     */
    List<TestStep> getTestSteps();
    
    /**
     * Add a test step to this test case.
     * 
     * @param testStep The test step to add
     */
    void addTestStep(TestStep testStep);
    
    /**
     * Get a test step by index.
     * 
     * @param index The step index
     * @return The test step at the given index
     */
    TestStep getStep(int index);
    
    /**
     * Go to a specific step index.
     * 
     * @param index The step index
     */
    void gotoStep(int index);
    
    /**
     * Mark this test case as failed.
     */
    void fail();
    
    /**
     * Mark this test case as skipped.
     */
    void skip();
    
    /**
     * Run this test case.
     */
    void run();
    
    /**
     * Get the status of this test case.
     * 
     * @return The test case status
     */
    TestItemStatus getStatus();
}