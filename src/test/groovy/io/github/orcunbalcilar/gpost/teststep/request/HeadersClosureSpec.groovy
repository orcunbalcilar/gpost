package io.github.orcunbalcilar.gpost.teststep.request

import io.github.orcunbalcilar.gpost.testcase.TestCase
import io.github.orcunbalcilar.gpost.testcase.TestCaseBuilder
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.PostTestStep
import spock.lang.Specification

class HeadersClosureSpec extends Specification {

    def "headers dsl should work"() {
        given:
        TestCase testCase = TestCaseBuilder.testCase("test case") {
            post {
                request {
                    context.contentType = "application/json"
                    println(context.contentType)
                    headers {
                        'Content-Type' "${context.contentType}"
                        'IntValue' 1
                        'NullValue' null
                    }
                }
            }
        }

        when:
        Map<String, String> headers = ((PostTestStep) testCase.step(0)).getRequest().createHeaders()
        println(headers)

        then:
        headers.get("Content-Type") == "application/json"
        headers.get("IntValue") == "1"
        headers.get("NullValue") == "null"
    }

    def "run should call the closure"() {
        given:
        def context = Mock(TestCaseRunContext)
        def closure = Mock(Closure)
        def lazyMapClosure = new HeadersClosure(context, closure)

        when:
        lazyMapClosure.run()

        then:
        1 * closure.call()
    }

    def "result should return headers map"() {
        given:
        def context = Mock(TestCaseRunContext)
        def closure = Mock(Closure)
        def lazyMapClosure = new HeadersClosure(context, closure)
        lazyMapClosure.Authorization "Bearer token"

        when:
        def result = lazyMapClosure.result()
        println(result)

        then:
        result == ["Authorization": "Bearer token"]
    }

}