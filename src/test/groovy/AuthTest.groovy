import com.epam.reportportal.junit5.ReportPortalExtension
import groovy.util.logging.Slf4j
import orcun.balcilar.gpost.teststep.request.auth.BasicAuth
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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
}
