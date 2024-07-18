package io.github.orcunbalcilar.gpost.teststep.response

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.HttpTestStep
import org.apache.groovy.json.internal.LazyMap

@CompileStatic
class JsonAssertions extends Assertions {
    JsonAssertions(HttpTestStep testStep, TestCaseRunContext context) {
        super(testStep, context)
    }

    @Override
    def getResponse() { new JsonSlurper().parseText(testStep.response) }

    void equals(Object actual, Object expected) {
        assert actual == expected, "Expected: ${expected}, actual: ${actual}"
    }

    void size(LazyMap actual, int expected) { size(actual.values(), expected) }

    void size(Collection<?> actual, int expected) {
        assert actual.size() == expected, "Expected size: ${expected}, actual: ${actual} -> size: ${actual.size()}"
    }
}
