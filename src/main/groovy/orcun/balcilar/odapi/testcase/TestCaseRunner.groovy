package orcun.balcilar.odapi.testcase

import orcun.balcilar.odapi.testcases.impl.TestCase
import orcun.balcilar.odapi.testcases.impl.TestItemStatus

class TestCaseRunner {
    static TestItemStatus run(TestCase testCase) {
        testCase.run()
        return testCase.getStatus()
    }
}
