package OnlineTicket


import io.github.orcunbalcilar.gpost.testcase.TestCase
import io.github.orcunbalcilar.gpost.test.BaseGroovyWireMockTest
import org.junit.jupiter.api.Test

import static io.github.orcunbalcilar.gpost.testcase.TestCaseBuilder.testCase

/**
 * Test using WireMock virtual services instead of external APIs.
 * This test has been updated to use a mock URL instead of external services.
 */
class GetAvailabilityTest extends BaseGroovyWireMockTest {

    @Test
    void run() {
        TestCase testCase = create()
        testCase.run()
        assert testCase.status.name() == 'PASSED'
    }

    TestCase create() {
        String mockUrl = getBaseUrl()
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
