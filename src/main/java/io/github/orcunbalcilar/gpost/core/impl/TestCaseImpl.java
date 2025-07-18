package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.TestItemStatus;
import io.github.orcunbalcilar.gpost.core.TestCase;
import io.github.orcunbalcilar.gpost.core.TestStep;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Java implementation of TestCase.
 */
public class TestCaseImpl implements TestCase {
    
    private static final Logger logger = Logger.getLogger(TestCaseImpl.class.getName());
    
    private String name;
    private final List<TestStep> testSteps = new ArrayList<>();
    private int currentStepIndex = 0;
    private TestItemStatus status = TestItemStatus.UNKNOWN;
    
    public TestCaseImpl() {
        // Default constructor
    }
    
    public TestCaseImpl(String name) {
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
    public List<TestStep> getTestSteps() {
        return testSteps;
    }
    
    @Override
    public void addTestStep(TestStep testStep) {
        testSteps.add(testStep);
    }
    
    @Override
    public TestStep getStep(int index) {
        return testSteps.get(index);
    }
    
    @Override
    public void gotoStep(int index) {
        this.currentStepIndex = index;
        logger.info("Going to step: " + testSteps.get(index).getName());
    }
    
    @Override
    public void fail() {
        this.status = TestItemStatus.FAILED;
        for (int i = currentStepIndex; i < testSteps.size(); i++) {
            testSteps.get(i).setDisabled(true);
        }
    }
    
    @Override
    public void skip() {
        this.status = TestItemStatus.SKIPPED;
        for (int i = currentStepIndex; i < testSteps.size(); i++) {
            testSteps.get(i).setDisabled(true);
        }
    }
    
    @Override
    public void run() {
        while (currentStepIndex < testSteps.size() && !testSteps.get(currentStepIndex).isDisabled()) {
            TestStep currentTestStep = testSteps.get(currentStepIndex);
            currentStepIndex++;
            currentTestStep.run();
        }
        
        if (testSteps.stream().allMatch(step -> step.getStatus() == TestItemStatus.PASSED)) {
            logger.info(name + " -> PASSED");
            this.status = TestItemStatus.PASSED;
        } else if (status == TestItemStatus.SKIPPED) {
            logger.info(name + " -> SKIPPED");
        } else {
            logger.severe(name + " -> FAILED");
            this.status = TestItemStatus.FAILED;
        }
    }
    
    @Override
    public TestItemStatus getStatus() {
        return status;
    }
}