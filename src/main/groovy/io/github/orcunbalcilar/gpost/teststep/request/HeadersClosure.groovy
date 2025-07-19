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
        Object[] argsArray = args as Object[]
        if (argsArray && argsArray.length > 0) {
            return headers.put(name, argsArray[0].toString())
        } else {
            return headers.put(name, "")
        }
    }

    Map<String, String> call() {
        closure.call()
        headers
    }
    
    void run() {
        closure.call()
    }
    
    Map<String, String> result() {
        return headers
    }
}
