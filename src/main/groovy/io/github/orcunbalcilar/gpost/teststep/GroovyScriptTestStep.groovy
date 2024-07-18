package io.github.orcunbalcilar.gpost.teststep

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.testcase.ContextAccess
import orcun.balcilar.gpost.TestItemStatus
import orcun.balcilar.gpost.testcase.TestCase
import orcun.balcilar.gpost.testcase.TestCaseRunContext
import org.slf4j.Logger

@CompileStatic
@Slf4j(value = "logger")
class GroovyScriptTestStep extends TestStep implements ContextAccess {
    private final Closure script
    final TestCase testCase
    final TestCaseRunContext context

    GroovyScriptTestStep(Closure script, TestCase testCase, TestCaseRunContext context) {
        this.script = script
        this.testCase = testCase
        this.context = context
    }

    Logger getLog() { logger }

    void name(String name) { this.name = name }

    @Override
    void run() {
        try {
            script.call()
            setStatus(TestItemStatus.PASSED)
        } catch (Exception | Error e) {
            log.error("Error while running test step: " + name, e)
            setStatus(TestItemStatus.FAILED)
        } finally {
            log.info("$name -> ${status.toString()}")
        }
    }
}
