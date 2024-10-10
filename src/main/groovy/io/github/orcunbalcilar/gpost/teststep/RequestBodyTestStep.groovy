package io.github.orcunbalcilar.gpost.teststep

import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.request.RequestWithBody

abstract class RequestBodyTestStep extends HttpTestStep {

    RequestBodyTestStep(TestCaseRunContext context, Closure closure) {
        super(context, closure)
    }

    @Override
    void request(@DelegatesTo(value = RequestWithBody, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.request = new RequestWithBody(context)
        closure.delegate = request
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
    }

}
