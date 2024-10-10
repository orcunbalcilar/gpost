package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.teststep.GetTestStep
import io.github.orcunbalcilar.gpost.teststep.GroovyScriptTestStep
import io.github.orcunbalcilar.gpost.teststep.PostTestStep
import io.github.orcunbalcilar.gpost.teststep.PutTestStep
import io.github.orcunbalcilar.gpost.teststep.request.auth.HasAuth

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
        testCase << postTestStep
    }

    void get(@DelegatesTo(value = GetTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        closure.curry(context)
        GetTestStep getTestStep = new GetTestStep(context, closure)
        closure.delegate = getTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        testCase << getTestStep
    }

    void put(@DelegatesTo(value = PutTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        PutTestStep putTestStep = new PutTestStep(context, closure)
        closure.delegate = putTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        testCase << putTestStep
    }

    void script(String name, @DelegatesTo(value = GroovyScriptTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        GroovyScriptTestStep groovyScriptTestStep = new GroovyScriptTestStep(closure, testCase, context)
        groovyScriptTestStep.name(name)
        closure.delegate = groovyScriptTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        testCase << groovyScriptTestStep
    }
}
