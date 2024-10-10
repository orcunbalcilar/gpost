package io.github.orcunbalcilar.gpost.teststep.request.body

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.ContextAccess
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
class RequestBodyBuilder implements ContextAccess {

    final TestCaseRunContext context
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

    void soap(@DelegatesTo(value = ContextAccess, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.body = new XmlRequestBody(context, {
            it.invokeMethod("soap:Envelope", new Object[]{["xmlns:soap": "http://schemas.xmlsoap.org/soap/envelope/"], {
                it.invokeMethod("soap:Body", closure)
            }})
        })
    }

    RequestBody getBody() { body }
}
