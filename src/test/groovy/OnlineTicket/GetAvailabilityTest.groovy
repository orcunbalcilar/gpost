package OnlineTicket

import com.epam.reportportal.junit5.ReportPortalExtension
import io.github.orcunbalcilar.gpost.ArgumentsSourceProvider
import io.github.orcunbalcilar.gpost.JUnit5TestSuite
import io.github.orcunbalcilar.gpost.testcase.TestCase
import org.junit.jupiter.api.extension.ExtendWith

import java.time.LocalDate

import static io.github.orcunbalcilar.gpost.testcase.TestCaseBuilder.testCase

@ExtendWith(ReportPortalExtension.class)
class GetAvailabilityTest extends JUnit5TestSuite<Integer> {

    GetAvailabilityTest() {
        println("GetAvailabilityTest constructor")
        ArgumentsSourceProvider.STREAM = (1..20).stream()
    }

    @Override
    TestCase create(Integer input) {
        testCase "LinkedIn Search Test - $input", {
            script("Setup") {
                log.info("Script-0 is running")
                context.lang = "Java"
            }
            get {
                url "https://checkout.hepsiburada.com/api/basket/count"
                request {
                    headers {
                        put "Accept", "application/json"
                        put "Content-Type", "application/json"
                    }
                }
                assertions {
                    statusCode 403
                }
            }
            get {
                url "https://checkout.hepsiburada.com/api/basket/count"
                request {
                    headers {
                        put "Accept", "application/json"
                        put "Content-Type", "application/json"
                    }
                }
                assertions {
                    statusCode 403
                }
            }
            script "Script-1", {
                log.info("Script-1 is running")
                log.info(context["date"] as String)
            }
            script("Script-2") {
                log.info("Script-2 is running")
                log.info(context["date"] as String)
            }
            script("Script-3") {
                log.info("Script-3 is running")
                log.info(context["date"] as String)
            }
            script("Script-4") {
                log.info("Script-4 is running")
                log.info(context["date"] as String)
                if (context.date == LocalDate.now()) {
                    log.info("Date is today")
                    testCase.gotoStep(3)
                }
                context.date = LocalDate.now().plusDays(1)
                log.info("Date is changed to tomorrow -> ${context.date}")
            }
        }
    }
}
