package io.github.orcunbalcilar.gpost.teststep.request

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.request.body.RequestBody
import io.github.orcunbalcilar.gpost.teststep.request.body.RequestBodyBuilder

@CompileStatic
class RequestWithBody extends Request {

    private RequestBodyBuilder bodyBuilder

    RequestWithBody(TestCaseRunContext context) {
        super(context)
    }

    void body(@DelegatesTo(value = RequestBodyBuilder, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.bodyBuilder = new RequestBodyBuilder(context)
        closure.delegate = bodyBuilder
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
    }

    RequestBody getBody() { bodyBuilder.body }

    String getBodyContent() { bodyBuilder.body.build() }
}
