package io.github.orcunbalcilar.gpost

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCase
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunner
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.Test

@CompileStatic
abstract class JUnit5Test {

    abstract TestCase create()

    @Test
    void run() {
        TestCase testCase = create()
        TestItemStatus status = TestCaseRunner.run(testCase)
        if (status == TestItemStatus.SKIPPED) {
            Assumptions.assumeTrue(false, "Test case : $testCase.name has been skipped")
        } else {
            assert status == TestItemStatus.PASSED, "$testCase.name has failed"
        }
    }
}
