package orcun.balcilar.odapi.testcases.impl

import groovy.util.logging.Slf4j

@Slf4j
class TestCase {
    String name
    private Map properties = [:]
    private List<TestStep> testSteps = []
    private List<TestStep> currentTestSteps = []
    private int currentStepIndex = 0
    private TestItemStatus status = TestItemStatus.UNKNOWN

    Map getProperties() { properties }

    void leftShift(TestStep testStep) {
        testSteps << testStep
    }

    void gotoStep(int index) {
        this.currentStepIndex = index
        //int currentStepIndex = testSteps.findIndexOf { it == currentTestSteps[0] }
        println("Current step index: " + currentStepIndex)
        /*if (currentStepIndex < index) {
            currentTestSteps >> testSteps[index..currentStepIndex]
            println("Current test steps: " + currentTestSteps*.name.join(", "))
        }*/
    }

    void run() {
        log.info("Running test case: " + name)
        println("Test steps: " + testSteps*.name.join(", "))
        //currentTestSteps = testSteps
        while (testSteps.size() > currentStepIndex) {
            testSteps[currentStepIndex].run()
            currentStepIndex++
        }
        if (testSteps.every { it.getStatus() == TestItemStatus.PASSED }) {
            log.error("passed")
            this.status = TestItemStatus.PASSED
        } else {
            log.error("failed")
            this.status = TestItemStatus.FAILED
        }
        log.info("Test case is finished: " + name)
        log.error("Test case status: " + getStatus())
    }

    TestItemStatus getStatus() { status }
}
