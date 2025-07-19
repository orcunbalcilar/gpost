package io.github.orcunbalcilar.gpost.testcase

import io.github.orcunbalcilar.gpost.core.TestCaseRunContext
import io.github.orcunbalcilar.gpost.core.impl.TestCaseRunContextImpl
import spock.lang.Specification

/**
 * Test specification for TestCaseSpec Groovy DSL features,
 * specifically the context access and dynamic property functionality.
 */
class TestCaseSpecSpec extends Specification {

    TestCaseSpec testCaseSpec
    TestCaseRunContext context

    def setup() {
        context = new TestCaseRunContextImpl()
        testCaseSpec = new TestCaseSpec(context)
    }

    def "getContext should return the test case run context"() {
        when:
        def result = testCaseSpec.getContext()

        then:
        result == context
        result instanceof TestCaseRunContext
    }

    def "methodMissing should return property value when no args provided"() {
        given:
        context.setProperty("testProperty", "testValue")

        when:
        def result = testCaseSpec.testProperty()

        then:
        result == "testValue"
    }

    def "methodMissing should set property value when one arg provided"() {
        when:
        def result = testCaseSpec.testProperty("newValue")

        then:
        result == testCaseSpec // Should return this for method chaining
        context.getProperty("testProperty") == "newValue"
    }

    def "methodMissing should throw MissingMethodException for non-existent property"() {
        when:
        testCaseSpec.nonExistentProperty()

        then:
        thrown(MissingMethodException)
    }

    def "methodMissing should throw MissingMethodException for wrong number of args"() {
        given:
        context.setProperty("testProperty", "testValue")

        when:
        testCaseSpec.testProperty("arg1", "arg2")

        then:
        thrown(MissingMethodException)
    }

    def "propertyMissing getter should return property value"() {
        given:
        context.setProperty("dynamicProp", "dynamicValue")

        when:
        def result = testCaseSpec.dynamicProp

        then:
        result == "dynamicValue"
    }

    def "propertyMissing getter should throw MissingPropertyException for non-existent property"() {
        when:
        testCaseSpec.nonExistentProp

        then:
        thrown(MissingPropertyException)
    }

    def "propertyMissing setter should set property value"() {
        when:
        testCaseSpec.newProperty = "setValue"

        then:
        context.getProperty("newProperty") == "setValue"
    }

    def "dynamic property access should work with complex scenarios"() {
        when:
        testCaseSpec.baseUrl = "http://localhost:8089"
        testCaseSpec.apiKey = "secret-key-123"
        testCaseSpec.timeout = 5000

        then:
        testCaseSpec.baseUrl == "http://localhost:8089"
        testCaseSpec.apiKey == "secret-key-123"
        testCaseSpec.timeout == 5000
    }

    def "method chaining should work with dynamic property setters"() {
        when:
        def result = testCaseSpec.baseUrl("http://example.com")
                                 .apiKey("key123")

        then:
        result == testCaseSpec
        context.getProperty("baseUrl") == "http://example.com"
        context.getProperty("apiKey") == "key123"
    }
}