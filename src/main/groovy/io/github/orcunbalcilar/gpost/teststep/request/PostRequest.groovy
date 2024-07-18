package io.github.orcunbalcilar.gpost.teststep.request

import groovy.transform.CompileStatic
import orcun.balcilar.gpost.testcase.TestCaseRunContext
import orcun.balcilar.gpost.teststep.request.body.RequestBodyBuilder

@CompileStatic
class PostRequest extends Request implements WithBody {

    private RequestBodyBuilder bodyBuilder

    PostRequest(TestCaseRunContext context) {
        super(context)
    }

    void body(@DelegatesTo(value = RequestBodyBuilder, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.bodyBuilder = new RequestBodyBuilder(context)
        closure.delegate = bodyBuilder
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
    }

    @Override
    String getBodyContent() { bodyBuilder.body.build() }
}
