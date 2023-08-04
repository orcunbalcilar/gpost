package orcun.balcilar.odapi.testcases.impl

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult
import org.slf4j.Logger

//@CompileStatic
@Slf4j
class Assertions {
    private final SoapRequestTestStep testStep
    private final TestCaseRunContext context
    private static final Closure<Closure<Boolean>> withName = { String name -> return { it.name() == name } }

    Assertions(SoapRequestTestStep testStep, TestCaseRunContext context) {
        this.testStep = testStep
        this.context = context
    }

    def context(String name) { context.context(name) }

    Logger getLog() { return log }

    void statusCode(int code) {
        assert testStep.statusCode == code, "Expected status code: ${code}, actual: ${testStep.statusCode}"
    }

    void size(List<GPathResult> actual, int expected) {
        assert actual.size() == expected, "Expected size: ${expected}, actual: ${actual}"
    }

    void value(GPathResult actual, String expected) {
        assert actual.text() == expected, "Expected value: ${expected}, actual: ${actual.text()}"
    }

    GPathResult attribute(GPathResult actual, String name) {
        assert name != null, "Attribute ${name} not found"
        return (GPathResult) actual.getProperty("@${name}")
    }

    GPathResult node(GPathResult actual, String name) {
        assert name != null, "Node ${name} not found"
        return (GPathResult) actual.getProperty(name)
    }

    GPathResult node(String name) {
        assert name != null, "Node ${name} not found"
        log.info("heyoooo")
        return (GPathResult) response.depthFirst().find(withName(name))
    }

    List<GPathResult> nodes(String name) { response.depthFirst().findAll withName(name) }

    GPathResult getResponse() { new XmlSlurper().parseText(testStep.response) }

    Closure<Boolean> byName(String name) { withName(name) }
}
