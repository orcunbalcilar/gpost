package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic

@CompileStatic
class TestCaseBuilder {
    static TestCase testCase(String name, @DelegatesTo(value = TestCaseSpec, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        TestCase testCase = new TestCase()
        testCase.name = name
        TestCaseSpec spec = new TestCaseSpec(testCase)
        closure.delegate = spec
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure()
        return testCase
    }
}
