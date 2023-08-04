package orcun.balcilar.odapi.testcase

import groovy.util.logging.Log4j2
import orcun.balcilar.odapi.testcases.impl.TestStep
import orcun.balcilar.odapi.testcases.impl.TestItemStatus

import java.time.LocalDateTime

@Log4j2
class TestCases {
    String name
    TestRunContext context
    private List<TestStep> currentTestSteps = []
    private final List<TestStep> testSteps
    Map headers = [:]

    private TestCaseStatus status = TestCaseStatus.UNKNOWN

    TestCases() {
        testSteps = []
    }

    TestCases(List<TestStep> testSteps) {
        this.testSteps = testSteps
        this.currentTestSteps = testSteps
    }

    void gotoStep(int index) {
        int currentStepIndex = testSteps.findIndexOf { it == currentTestSteps[0] }
        println("Current step index: " + currentStepIndex)
        if (currentStepIndex < index) {
            currentTestSteps >> testSteps[index..currentStepIndex]
            println("Current test steps: " + currentTestSteps*.name.join(", "))
        }
    }

    void run() {
        println(LocalDateTime.now().toString() + ": Running test case: " + name)
        currentTestSteps = testSteps
        while (currentTestSteps && this.status == TestCaseStatus.UNKNOWN) {
            currentTestSteps.remove(0).run()
        }
        if (testSteps.every { it.status == TestItemStatus.PASSED }) {
            this.status = TestCaseStatus.PASSED
        } else {
            this.status = TestCaseStatus.FAILED
        }
        println(LocalDateTime.now().toString() + ": Test case is finished: " + name)
        println("Test case status: " + this.status)
    }

    private static enum TestCaseStatus {
        UNKNOWN, PASSED, FAILED, CANCELLED
    }
}
