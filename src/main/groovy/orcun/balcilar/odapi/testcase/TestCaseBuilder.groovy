package orcun.balcilar.odapi.testcase

import groovy.transform.CompileStatic
import orcun.balcilar.odapi.testcases.impl.TestCase
import orcun.balcilar.odapi.testcases.impl.TestCaseSpec

@CompileStatic
class TestCaseBuilder {
    static TestCase testCase(@DelegatesTo(value = TestCaseSpec, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        TestCase testCase = new TestCase()
        TestCaseSpec spec = new TestCaseSpec(testCase)
        closure.delegate = spec
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure()
        return testCase
    }
}
