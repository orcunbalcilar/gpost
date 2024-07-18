package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.TestItemStatus

@CompileStatic
class TestCaseRunner {
    static TestItemStatus run(TestCase testCase) {
        testCase.run()
        return testCase.getStatus()
    }
}
