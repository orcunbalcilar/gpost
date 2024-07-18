package io.github.orcunbalcilar.gpost.teststep.request

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.ContextAccess
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
class LazyMapClosure implements ContextAccess {
    @Delegate
    private final Map<String, String> headers = [:]

    final TestCaseRunContext context

    private final Closure closure

    LazyMapClosure(TestCaseRunContext context, Closure closure) {
        this.context = context
        this.closure = closure
    }

    void run() { closure.call() }

    Map<String, String> result() { headers }
}
