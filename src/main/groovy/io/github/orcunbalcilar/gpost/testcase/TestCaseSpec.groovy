package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.teststep.GetTestStep
import io.github.orcunbalcilar.gpost.teststep.GroovyScriptTestStep
import io.github.orcunbalcilar.gpost.teststep.PostTestStep
import orcun.balcilar.gpost.teststep.request.auth.HasAuth

@CompileStatic
@Slf4j
class TestCaseSpec implements HasAuth {
    private final TestCase testCase
    private final TestCaseRunContext context = new TestCaseRunContext()

    TestCaseSpec(TestCase testCase) {
        this.testCase = testCase
    }

    void post(@DelegatesTo(value = PostTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        PostTestStep postTestStep = new PostTestStep(context, closure)
        closure.delegate = postTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.run()
        testCase << postTestStep
    }

    void get(@DelegatesTo(value = GetTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        GetTestStep getTestStep = new GetTestStep(context, closure)
        closure.delegate = getTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.run()
        testCase << getTestStep
    }

    void script(String name, @DelegatesTo(value = GroovyScriptTestStep, strategy = Closure.DELEGATE_FIRST) Closure closure) {
        GroovyScriptTestStep groovyScriptTestStep = new GroovyScriptTestStep(closure, testCase, context)
        groovyScriptTestStep.name(name)
        closure.delegate = groovyScriptTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        testCase << groovyScriptTestStep
    }
}
