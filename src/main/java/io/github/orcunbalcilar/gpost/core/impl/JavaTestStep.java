package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.TestItemStatus;
import io.github.orcunbalcilar.gpost.core.TestStep;

/**
 * Base Java implementation of TestStep.
 */
public abstract class JavaTestStep implements TestStep {
    
    private String name;
    private boolean disabled = false;
    private TestItemStatus status = TestItemStatus.UNKNOWN;
    
    public JavaTestStep() {
        // Default constructor
    }
    
    public JavaTestStep(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean isDisabled() {
        return disabled;
    }
    
    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    @Override
    public TestItemStatus getStatus() {
        return status;
    }
    
    protected void setStatus(TestItemStatus status) {
        this.status = status;
    }
    
    @Override
    public abstract void run();
}