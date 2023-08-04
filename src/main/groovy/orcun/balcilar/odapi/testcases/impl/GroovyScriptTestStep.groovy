package orcun.balcilar.odapi.testcases.impl

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.slf4j.Logger

@CompileStatic
@Slf4j
class GroovyScriptTestStep extends TestStep {
    private final Closure script
    private final TestCase testCase
    @Delegate
    private final TestCaseRunContext context

    GroovyScriptTestStep(Closure script, TestCase testCase, TestCaseRunContext context) {
        this.script = script
        this.testCase = testCase
        this.context = context
    }

    TestCase getTestCase() { testCase }

    Logger getLog() { return log }

    void name(String name) { this.name = name }

    @Override
    void run() {
        try {
            script()
            status = TestItemStatus.PASSED
        } catch (Exception e) {
            log.error("Error while running test step: " + name, e)
            status = TestItemStatus.FAILED
        }
    }
}
