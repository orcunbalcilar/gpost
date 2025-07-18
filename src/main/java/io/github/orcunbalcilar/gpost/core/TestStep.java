package io.github.orcunbalcilar.gpost.core;

import io.github.orcunbalcilar.gpost.TestItemStatus;

/**
 * Core interface for test steps.
 * Both Groovy and Java DSL implementations should implement this interface.
 */
public interface TestStep {
    
    /**
     * Get the name of the test step.
     * 
     * @return The test step name
     */
    String getName();
    
    /**
     * Set the name of the test step.
     * 
     * @param name The test step name
     */
    void setName(String name);
    
    /**
     * Check if this test step is disabled.
     * 
     * @return True if disabled, false otherwise
     */
    boolean isDisabled();
    
    /**
     * Set the disabled state of this test step.
     * 
     * @param disabled True to disable, false to enable
     */
    void setDisabled(boolean disabled);
    
    /**
     * Run this test step.
     */
    void run();
    
    /**
     * Get the status of this test step.
     * 
     * @return The test step status
     */
    TestItemStatus getStatus();
}