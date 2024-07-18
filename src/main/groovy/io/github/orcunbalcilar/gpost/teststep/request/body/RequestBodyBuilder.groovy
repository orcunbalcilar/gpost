package io.github.orcunbalcilar.gpost.teststep.request.body

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.ContextAccess
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
class RequestBodyBuilder implements ContextAccess {

    private final TestCaseRunContext context
    private RequestBody body

    RequestBodyBuilder(TestCaseRunContext context) {
        this.context = context
    }

    void json(@DelegatesTo(value = ContextAccess, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.body = new JsonRequestBody(context, closure)
    }

    void xml(@DelegatesTo(value = ContextAccess, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.body = new XmlRequestBody(context, closure)
    }

    @Override
    TestCaseRunContext getContext() { context }

    RequestBody getBody() { body }
}
