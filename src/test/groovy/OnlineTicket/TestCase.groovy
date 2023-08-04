package OnlineTicket

import groovy.transform.CompileStatic
import groovy.xml.XmlUtil
import orcun.balcilar.odapi.testcases.impl.TestCase
import orcun.balcilar.odapi.testcases.impl.TestItemStatus
import org.junit.jupiter.api.Test

import static orcun.balcilar.odapi.testcase.TestCaseBuilder.testCase

@CompileStatic
class GetAvailabilityTest {
    @Test
    void testCase() {
        TestCase t = testCase {
            name "GetAvailability"
            context "date", "23AUG2023"
            post {
                request '<person></person>'
                to ""
                name ""
                basicAuth {
                    username "user"
                    password "pass"
                }
                assertions {
                    log.info XmlUtil.serialize(response)
                    statusCode 200
                    size nodes("person"), 1
                }
            }
            script {
                name "Script-1"
                log.info("Script is running")
                log.info(testCase.name)
            }
        }
        t.run()
        assert t.status == TestItemStatus.PASSED, "Test case failed"
    }
}
