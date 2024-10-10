package io.github.orcunbalcilar.gpost.teststep.request

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.ContextAccess
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
class HeadersClosure implements ContextAccess {
    final Map<String, String> headers = [:]

    final TestCaseRunContext context

    private final Closure closure

    HeadersClosure(TestCaseRunContext context, Closure closure) {
        this.context = context
        this.closure = closure
    }

    def methodMissing(String name, def args) {
        return headers.put(name, ((Object[]) args)?[0].toString())
    }

    Map<String, String> call() {
        closure.call()
        headers
    }
}
