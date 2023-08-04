package orcun.balcilar.odapi.testcases.impl

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j2
import orcun.balcilar.odapi.auth.HasAuth

@CompileStatic
@Log4j2
class TestCaseSpec implements HasAuth {
    private final TestCase testCase
    @Delegate
    private final TestCaseRunContext context = new TestCaseRunContext()

    TestCaseSpec(TestCase testCase) {
        this.testCase = testCase
    }

    void name(String name) { testCase.name = name }

    void steps(@DelegatesTo(value = TestCaseSpec, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        closure.run()
    }

    void post(@DelegatesTo(value = SoapRequestTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        SoapRequestTestStep soapRequestTestStep = new SoapRequestTestStep(context)
        closure.delegate = soapRequestTestStep
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.run()
        testCase << soapRequestTestStep
    }

    void script(@DelegatesTo(value = GroovyScriptTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        GroovyScriptTestStep groovyScriptTestStep = new GroovyScriptTestStep(closure, testCase, context)
        closure.delegate = groovyScriptTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        testCase << groovyScriptTestStep
    }
}
