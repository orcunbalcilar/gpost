package io.github.orcunbalcilar.gpost.teststep.response

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.HttpTestStep

@CompileStatic
@Slf4j
class Assertions {
    protected final HttpTestStep testStep
    @Delegate
    final TestCaseRunContext context
    //private static final Closure<Closure<Boolean>> withName = { String name -> return { it.name() == name } }

    Assertions(HttpTestStep testStep, TestCaseRunContext context) {
        this.testStep = testStep
        this.context = context
    }

    void json(@DelegatesTo(value = ResponseBodyAssertions, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        def json = new JsonSlurper().parseText(testStep.response.bodyText)
        ResponseBodyAssertions bodyAssertions = new ResponseBodyAssertions(json, context)
        closure.delegate = bodyAssertions
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    void xml(@DelegatesTo(value = ResponseBodyAssertions, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        GPathResult xml = new XmlSlurper().parseText(testStep.response.bodyText)
        ResponseBodyAssertions bodyAssertions = new ResponseBodyAssertions(xml, context)
        closure.delegate = bodyAssertions
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    private void bodyAssertions(def response, Closure closure) {
        ResponseBodyAssertions bodyAssertions = new ResponseBodyAssertions(response, context)
        closure.delegate = bodyAssertions
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.call()
    }

    String getBodyText() {
        return testStep.response.bodyText
    }

    void statusCode(int code) {
        int statusCode = testStep.statusCode
        assert statusCode == code, "Expected status code: ${code}, actual: ${statusCode}"
        log.info("Status code assertion : ${statusCode}")
    }

    void equals(String actual, String expected) {
        assert actual == expected, "Expected value: ${expected}, actual: ${actual}"
    }

    /* void size(List<GPathResult> actual, int expected) {
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

     Closure<Boolean> byName(String name) { withName(name) }*/
}
