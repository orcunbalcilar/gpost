package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.TestItemStatus
import io.github.orcunbalcilar.gpost.teststep.TestStep

@CompileStatic
@Slf4j
class TestCase {
    String name
    final List<TestStep> testSteps = []
    private int currentStepIndex = 0
    private TestItemStatus status = TestItemStatus.UNKNOWN

    void leftShift(TestStep testStep) {
        testSteps << testStep
    }

    TestStep step(int index) { testSteps[index] }

    void gotoStep(int index) {
        this.currentStepIndex = index
        log.info("Going to step: " + testSteps[index].getName())
    }

    void fail() {
        this.status = TestItemStatus.FAILED
        testSteps[currentStepIndex..-1].each { it.disabled = true }
    }

    void skip() {
        this.status = TestItemStatus.SKIPPED
        testSteps[currentStepIndex..-1].each { it.disabled = true }
    }

    void run() {
        //log.info("Running test case: " + name)
        //log.info("Test steps: " + testSteps*.getName().join(", "))
        while (currentStepIndex < testSteps.size() && !(testSteps[currentStepIndex]).disabled) {
            TestStep currentTestStep = testSteps[currentStepIndex]
            currentStepIndex++
            currentTestStep.run()
        }
        println(log.class)
        if (testSteps.every { it.getStatus() == TestItemStatus.PASSED }) {
            log.info("${name} -> PASSED")
            this.status = TestItemStatus.PASSED
        } else if (status == TestItemStatus.SKIPPED) {
            log.info("${name} -> SKIPPED")
        } else {
            log.error("${name} -> FAILED")
            this.status = TestItemStatus.FAILED
        }
    }

    TestItemStatus getStatus() { status }
}
