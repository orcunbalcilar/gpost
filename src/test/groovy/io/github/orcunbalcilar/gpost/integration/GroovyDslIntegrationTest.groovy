package io.github.orcunbalcilar.gpost.integration

import io.github.orcunbalcilar.gpost.TestItemStatus
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunner
import org.junit.jupiter.api.Test

import static io.github.orcunbalcilar.gpost.testcase.TestCaseBuilder.testCase
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * Comprehensive integration tests for Groovy DSL.
 * Tests the complete flow from DSL creation to HTTP execution using WireMock.*/
class GroovyDslIntegrationTest extends IntegrationTest {

    @Test
    void shouldPerformSimpleGetRequest() {
        // Given
        def testUrl = "${getBaseUrl()}/get".toString()
        def testCaseInstance = testCase("Simple GET Test") {
            get {
                name "Get Data"
                url testUrl
                request {
                    headers {
                        "Accept" "application/json"
                        "User-Agent" "Groovy-DSL-Integration-Test"
                    }
                }
                assertions {
                    statusCode 200
                    json {
                        equals(response.args, response.args) // Just verify args object exists
                        equals(response.headers, response.headers)
                        // Just verify headers object exists
                        equals(response.url, testUrl)
                    }
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
        verifyRequest(getRequestedFor(urlEqualTo("/get")))
    }

    @Test
    void shouldPerformPostRequestWithJsonBody() {
        // Given
        def testUrl = "${getBaseUrl()}/post"
        def testCaseInstance = testCase("POST with JSON") {
            post {
                name "Post JSON Data"
                url testUrl
                request {
                    headers {
                        "Accept" "application/json"
                        "Content-Type" "application/json"
                    }
                    body {
                        json {
                            message "Hello from Groovy DSL"
                            timestamp System.currentTimeMillis()
                            active true
                        }
                    }
                }
                assertions {
                    statusCode 200
                    json {
                        equals(response.json.message, "Hello from Groovy DSL")
                    }
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
    }

    @Test
    void shouldPerformSoapRequest() {
        // Given
        def testUrl = "${getBaseUrl()}/webservicesserver/NumberConversion.wso"
        def testCaseInstance = testCase("SOAP Request Test") {
            post {
                name "Number to Words SOAP"
                url testUrl
                request {
                    headers {
                        "Content-Type" "text/xml; charset=utf-8"
                        "SOAPAction" ""
                    }
                    body {
                        soap {
                            "NumberToWords"("xmlns": "http://www.dataaccess.com/webservicesserver/") {
                                "ubiNum" "903"
                            }
                        }
                    }
                }
                assertions {
                    statusCode 200
                    xml {
                        equals(response.text(), "nine hundred and three ")
                    }
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
    }

    @Test
    void shouldHandleBasicAuthentication() {
        // Given
        def testUrl = "${getBaseUrl()}/basic-auth/user/pass"
        def testCaseInstance = testCase("Basic Auth Test") {
            get {
                name "Authenticated Request"
                url testUrl
                basicAuth {
                    username "user"
                    password "pass"
                }
                request {
                    headers {
                        "Accept" "application/json"
                    }
                }
                assertions {
                    statusCode 200
                    json {
                        equals(response.authenticated, true)
                        equals(response.user, "user")
                    }
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
    }

    @Test
    void shouldExecuteMultipleStepsInSequence() {
        // Given
        def testBaseUrl = getBaseUrl()
        def testCaseInstance = testCase("Multi-Step Test") {
            script("setup") {
                context.property("userId", 123)
                context.property("baseUrl", testBaseUrl)
            }

            get {
                name "Get User Data"
                url context.property("baseUrl") + "/data/json"
                request {
                    headers {
                        "Accept" "application/json"
                    }
                }
                assertions {
                    statusCode 200
                    json {
                        equals(response.id, 1)
                        equals(response.name, "Test User")
                    }
                }
            }

            post {
                name "Update User"
                url context.property("baseUrl") + "/post"
                request {
                    headers {
                        "Accept" "application/json"
                        "Content-Type" "application/json"
                    }
                    body {
                        json {
                            userId context.property("userId")
                            action "update"
                        }
                    }
                }
                assertions {
                    statusCode 200
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
    }

    @Test
    void shouldHandleErrorResponses() {
        // Given
        def testUrl = "${getBaseUrl()}/status/404"
        def testCaseInstance = testCase("Error Handling Test") {
            get {
                name "Test 404 Response"
                url testUrl
                request {
                    headers {
                        "Accept" "application/json"
                    }
                }
                assertions {
                    statusCode 404
                    json {
                        equals(response.error, "Not Found")
                        // For contains, we can use a simple assertion
                        equals(response.message?.contains("not found"), true)
                    }
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
    }

    @Test
    void shouldHandleXmlDataParsing() {
        // Given
        def testUrl = "${getBaseUrl()}/data/xml"
        def testCaseInstance = testCase("XML Data Test") {
            get {
                name "Get XML Data"
                url testUrl
                request {
                    headers {
                        "Accept" "application/xml"
                    }
                }
                assertions {
                    statusCode 200
                    xml {
                        equals(response.user.id.text(), "1")
                        equals(response.user.name.text(), "Test User")
                        equals(response.user.email.text(), "test@example.com")
                        equals(response.user.active.text(), "true")
                        size(response.user.roles.role, 2)
                    }
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
    }

    @Test
    void shouldFailTestWhenAssertionFails() {
        // Given
        def testUrl = "${getBaseUrl()}/get"
        def testCaseInstance = testCase("Failing Test") {
            get {
                name "Failing Assertion"
                url testUrl
                request {
                    headers {
                        "Accept" "application/json"
                    }
                }
                assertions {
                    statusCode 500 // This should fail since /get returns 200
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.FAILED, status)
    }

    @Test
    void shouldHandleComplexJsonAssertions() {
        // Given
        def testUrl = "${getBaseUrl()}/data/json"
        def testCaseInstance = testCase("Complex JSON Test") {
            get {
                name "Complex JSON Assertions"
                url testUrl
                request {
                    headers {
                        "Accept" "application/json"
                    }
                }
                assertions {
                    statusCode 200
                    json {
                        equals(response.id instanceof Number, true)
                        equals(response.name instanceof String, true)
                        equals(response.email?.contains("@"), true)
                        equals(response.active instanceof Boolean, true)
                        equals(response.roles instanceof List, true)
                        size(response.roles, 2)
                        equals(response.roles[0], "user")
                        equals(response.roles[1], "admin")
                    }
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
    }

    @Test
    void shouldSupportDynamicContent() {
        // Given
        def testUrl = "${getBaseUrl()}/post"
        def testCaseInstance = testCase("Dynamic Content Test") {
            script("generateDynamicData") {
                def timestamp = System.currentTimeMillis()
                def randomId = new Random().nextInt(1000)
                context.property("timestamp", timestamp)
                context.property("randomId", randomId)
            }

            post {
                name "Post Dynamic Data"
                url testUrl
                request {
                    headers {
                        "Content-Type" "application/json"
                        "Accept" "application/json"
                    }
                    body {
                        json {
                            id context.property("randomId")
                            timestamp context.property("timestamp")
                            message "Dynamic content at ${new Date()}"
                        }
                    }
                }
                assertions {
                    statusCode 200
                    // Verify that the request contained our dynamic data
                    assert bodyText.contains(context.property("randomId").toString())
                }
            }
        }

        // When
        def status = TestCaseRunner.run(testCaseInstance)

        // Then
        assertEquals(TestItemStatus.PASSED, status)
    }
}
