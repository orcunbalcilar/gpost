import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import orcun.balcilar.odapi.auth.BasicAuth
import orcun.balcilar.odapi.testcases.impl.GroovyScriptTestStep
import org.junit.jupiter.api.Test

@Slf4j
class AuthTest {
    @Test
    void testAuth() {
        def auth = new BasicAuth()
        auth.username("username")
        auth.password("password")
        log.info(auth.getAuthString())
    }

    @CompileStatic
    @Test
    void script() {
        def a = 1
        GroovyScriptTestStep groovyScriptTestStep = new GroovyScriptTestStep({
            log.info("Script is running")
            log.info(a.toString())
        }, null, null)
        boolean result
        try {
            groovyScriptTestStep.run()
            result = true
        } catch (Throwable e) {
            e.printStackTrace()
            result = false
        }
        log.info(result.toString())
    }
}
