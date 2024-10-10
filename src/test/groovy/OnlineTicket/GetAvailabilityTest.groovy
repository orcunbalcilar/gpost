package OnlineTicket


import io.github.orcunbalcilar.gpost.JUnit5Test
import io.github.orcunbalcilar.gpost.testcase.TestCase

import static io.github.orcunbalcilar.gpost.testcase.TestCaseBuilder.testCase

//@ExtendWith(ReportPortalExtension.class)
class GetAvailabilityTest extends JUnit5Test {

    @Override
    TestCase create() {
        testCase "LinkedIn Search Test", {
            script("setUbiNum") {
                log.info("Script-1 is running")
                context.property("ert", 903)
            }
            post {
                url("https://www.dataaccess.com/webservicesserver/NumberConversion.wso")
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
