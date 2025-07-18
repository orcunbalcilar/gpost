package OnlineTicket


import io.github.orcunbalcilar.gpost.JUnit5Test
import io.github.orcunbalcilar.gpost.testcase.TestCase

import static io.github.orcunbalcilar.gpost.testcase.TestCaseBuilder.testCase

/**
 * Test using WireMock virtual services instead of external APIs.
 * This test has been updated to use a mock URL instead of external services.
 */
class GetAvailabilityTest extends JUnit5Test {

    @Override
    TestCase create() {
        // Use localhost mock instead of external service
        def mockUrl = "http://localhost:8090"
        
        testCase "LinkedIn Search Test", {
            script("setUbiNum") {
                log.info("Script-1 is running")
                context.property("ert", 903)
            }
            post {
                url("${mockUrl}/webservicesserver/NumberConversion.wso")
                request {
                    body {
                        soap {
                            "NumberToWords"("xmlns": "http://www.dataaccess.com/webservicesserver/") {
                                "ubiNum" context.property('ert')
                            }
                        }
                    }
                }
                assertions {
                    statusCode 200
                    xml {
                        equals(response.Body.NumberToWordsResponse.NumberToWordsResult.text(), 'nine hundred and three ')
                    }
                }
            }
        }
    }
}
