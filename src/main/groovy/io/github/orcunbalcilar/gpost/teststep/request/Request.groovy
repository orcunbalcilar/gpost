package io.github.orcunbalcilar.gpost.teststep.request

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
abstract class Request {
    private HeadersClosure headersClosure

    @Delegate
    final TestCaseRunContext context

    Request(TestCaseRunContext context) { this.context = context }

    void headers(@DelegatesTo(value = HeadersClosure, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        HeadersClosure headersClosure = new HeadersClosure(context, closure)
        closure.delegate = headersClosure
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        this.headersClosure = headersClosure
    }

    Map<String, String> createHeaders() { headersClosure ? headersClosure.call() : [:] }
}
