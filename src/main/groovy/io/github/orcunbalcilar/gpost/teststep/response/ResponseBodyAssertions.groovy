package io.github.orcunbalcilar.gpost.teststep.response

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
class ResponseBodyAssertions {
    final def response
    final TestCaseRunContext context

    ResponseBodyAssertions(def response, TestCaseRunContext context) {
        this.response = response
        this.context = context
    }

    void equals(Object actual, Object expected) {
        assert actual == expected, "Expected: ${expected}, actual: ${actual}"
    }

    void size(Collection<?> actual, int expected) {
        assert actual.size() == expected, "Expected size: ${expected}, actual: ${actual} -> size: ${actual.size()}"
    }
    
}
