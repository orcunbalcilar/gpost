package io.github.orcunbalcilar.gpost

import groovy.transform.CompileStatic
import orcun.balcilar.gpost.testcase.TestCase
import orcun.balcilar.gpost.testcase.TestCaseRunner
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@CompileStatic
abstract class JUnit5TestSuite<T> {

    abstract TestCase create(T input)

    @ParameterizedTest
    @MethodSource("orcun.balcilar.gpost.ArgumentsSourceProvider#get")
    void run(T input) {
        TestCase testCase = create(input)
        TestItemStatus status = TestCaseRunner.run(testCase)
        if (status == TestItemStatus.SKIPPED) {
            Assumptions.assumeTrue(false, "Test case : $testCase.name has been skipped")
        } else {
            assert status == TestItemStatus.PASSED, "$testCase.name has failed"
        }
    }
}
