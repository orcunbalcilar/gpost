package io.github.orcunbalcilar.gpost.teststep.request.body

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import org.apache.hc.core5.http.ContentType

@CompileStatic
class JsonRequestBody extends RequestBody {
    final Closure closure

    JsonRequestBody(TestCaseRunContext context, Closure closure) {
        super(context)
        closure.delegate = context
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        this.closure = closure
    }

    @Override
    ContentType getContentType() { ContentType.APPLICATION_JSON }

    @Override
    String build() {
        JsonBuilder jsonBuilder = new JsonBuilder()
        jsonBuilder.call(closure)
        jsonBuilder.toPrettyString()
    }
}
