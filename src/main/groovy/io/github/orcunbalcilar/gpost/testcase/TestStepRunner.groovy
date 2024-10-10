package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.teststep.GetTestStep
import io.github.orcunbalcilar.gpost.teststep.PostTestStep

@CompileStatic
class TestStepRunner {
    static void get(@DelegatesTo(value = GetTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        GetTestStep getTestStep = new GetTestStep(new TestCaseRunContext(), closure)
        closure.delegate = getTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        getTestStep.run()
    }

    static void post(@DelegatesTo(value = PostTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        PostTestStep postTestStep = new PostTestStep(new TestCaseRunContext(), closure)
        closure.delegate = postTestStep
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        postTestStep.run()
    }
}
