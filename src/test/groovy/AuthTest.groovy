import com.epam.reportportal.junit5.ReportPortalExtension
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.teststep.request.auth.BasicAuth
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.github.orcunbalcilar.gpost.testcase.TestStepRunner.get

@ExtendWith(ReportPortalExtension.class)
@Slf4j
class AuthTest {

    @Disabled
    @Test
    void testAuth() {
        def auth = new BasicAuth()
        auth.username("username")
        auth.password("password")
        log.info(auth.toString())
    }

    @Disabled
    @Test
    void get_http_request() {
        get {
            url "https://google-translate1.p.rapidapi.com/language/translate/v2/languages"
            request {
                headers {
                    "x-rapidapi-key" "cd2bc6cabbmsh62d8e19b3a5cb8bp1f611fjsn005b6f5a474f"
                    "x-rapidapi-host" "google-translate1.p.rapidapi.com"
                    "Accept-Encoding" "application/gzip"
                }
            }
            assertions {
                statusCode 403
                println("Response: ${response.toString()}")
            }
        }
    }
}
