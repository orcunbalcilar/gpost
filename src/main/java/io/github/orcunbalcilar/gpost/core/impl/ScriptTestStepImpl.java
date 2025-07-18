package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.TestItemStatus;

/**
 * Java implementation of a script test step.
 */
public class ScriptTestStepImpl extends TestStepImpl {
    
    private final Runnable script;
    
    public ScriptTestStepImpl(String name, Runnable script) {
        super(name);
        this.script = script;
    }
    
    @Override
    public void run() {
        try {
            script.run();
            setStatus(TestItemStatus.PASSED);
        } catch (Exception e) {
            setStatus(TestItemStatus.FAILED);
            throw new RuntimeException("Script execution failed: " + e.getMessage(), e);
        }
    }
}