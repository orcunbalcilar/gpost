package io.github.orcunbalcilar.gpost.testcase

import io.github.orcunbalcilar.gpost.testcase.TestCase
import spock.lang.Specification

/**
 * Test specification for TestCaseSpec Groovy DSL features,
 * specifically the context access and dynamic property functionality.
 * Note: Some dynamic features don't work with @CompileStatic annotation.
 */
class TestCaseSpecSpec extends Specification {

    TestCaseSpec testCaseSpec
    TestCase testCase

    def setup() {
        testCase = new TestCase()
        testCase.name = "Test Case"
        testCaseSpec = new TestCaseSpec(testCase)
    }

    def "getContext should return the test case run context"() {
        when:
        def result = testCaseSpec.getContext()

        then:
        result != null
        result.getClass().getSimpleName() == "TestCaseRunContext"
    }

    def "context should support property operations"() {
        when:
        testCaseSpec.getContext().setProperty("testProperty", "testValue")
        def result = testCaseSpec.getContext().getProperty("testProperty")

        then:
        result == "testValue"
    }

    def "context should support hasProperty check"() {
        given:
        testCaseSpec.getContext().setProperty("existingProperty", "value")

        expect:
        testCaseSpec.getContext().hasProperty("existingProperty") == true
        testCaseSpec.getContext().hasProperty("nonExistentProperty") == false
    }

    def "context should support property removal"() {
        given:
        testCaseSpec.getContext().setProperty("tempProperty", "tempValue")
        
        when:
        def removedValue = testCaseSpec.getContext().removeProperty("tempProperty")

        then:
        removedValue == "tempValue"
        testCaseSpec.getContext().hasProperty("tempProperty") == false
    }

    def "context should support typed property access"() {
        when:
        testCaseSpec.getContext().setProperty("stringProp", "stringValue")
        testCaseSpec.getContext().setProperty("intProp", 42)
        testCaseSpec.getContext().setProperty("boolProp", true)

        then:
        testCaseSpec.getContext().getProperty("stringProp", String.class) == "stringValue"
        testCaseSpec.getContext().getProperty("intProp", Integer.class) == 42
        testCaseSpec.getContext().getProperty("boolProp", Boolean.class) == true
    }

    def "context should support property clearing"() {
        given:
        testCaseSpec.getContext().setProperty("prop1", "value1")
        testCaseSpec.getContext().setProperty("prop2", "value2")

        when:
        testCaseSpec.getContext().clearProperties()

        then:
        testCaseSpec.getContext().hasProperty("prop1") == false
        testCaseSpec.getContext().hasProperty("prop2") == false
    }

    def "enhanced context access should work through getContext method"() {
        when:
        def context = testCaseSpec.getContext()
        context.setProperty("baseUrl", "http://localhost:8089")
        context.setProperty("apiKey", "secret-key-123")

        then:
        context.getProperty("baseUrl") == "http://localhost:8089"
        context.getProperty("apiKey") == "secret-key-123"
    }

    def "test case spec should maintain reference to test case"() {
        when:
        testCase.name = "Updated Test Name"

        then:
        testCaseSpec.testCase.name == "Updated Test Name"
    }

    def "test case spec should initialize with working context"() {
        expect:
        testCaseSpec.getContext() != null
        testCaseSpec.getContext().getClass().getSimpleName() == "TestCaseRunContext"
    }

    def "context should handle complex property scenarios"() {
        given:
        def complexValue = [
            url: "http://example.com",
            headers: ["Authorization": "Bearer token", "Content-Type": "application/json"],
            timeout: 5000
        ]

        when:
        testCaseSpec.getContext().setProperty("config", complexValue)
        def retrieved = testCaseSpec.getContext().getProperty("config")

        then:
        retrieved == complexValue
        retrieved.url == "http://example.com"
        retrieved.headers["Authorization"] == "Bearer token"
        retrieved.timeout == 5000
    }
}