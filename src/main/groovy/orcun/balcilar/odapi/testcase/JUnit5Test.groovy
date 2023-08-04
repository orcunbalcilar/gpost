package orcun.balcilar.odapi.testcase

import orcun.balcilar.odapi.testcases.impl.TestCase
import orcun.balcilar.odapi.testcases.impl.TestItemStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

abstract class JUnit5Test {
    abstract TestCase testCase()

    @DisplayName("Run test cases")
    @Test
    void run() {
        assert TestCaseRunner.run(testCase()) == TestItemStatus.PASSED
    }
}
