package io.github.orcunbalcilar.gpost.teststep.request.body

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.core.ContextAccess
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext as CoreTestCaseRunContext
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import org.apache.hc.core5.http.ContentType

@CompileStatic
abstract class RequestBody implements ContextAccess {
    final TestCaseRunContext context

    RequestBody(TestCaseRunContext context) {
        this.context = context
    }

    abstract ContentType getContentType()

    abstract String build()

    @Override
    CoreTestCaseRunContext getContext() {
        return context
    }
}
