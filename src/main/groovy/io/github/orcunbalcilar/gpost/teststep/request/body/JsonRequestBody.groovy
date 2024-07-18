package io.github.orcunbalcilar.gpost.teststep.request.body

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
class JsonRequestBody extends RequestBody {
    final Closure closure

    JsonRequestBody(TestCaseRunContext context, Closure closure) {
        this.context = context
        closure.delegate = context
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        this.closure = closure
    }

    String build() {
        JsonBuilder jsonBuilder = new JsonBuilder()
        jsonBuilder.call(closure)
        jsonBuilder.toPrettyString()
    }

    @Override
    TestCaseRunContext getContext() { return this.@context }
}
