package io.github.orcunbalcilar.gpost.teststep.response

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.HttpTestStep

@CompileStatic
@Slf4j
abstract class Assertions {
    protected final HttpTestStep testStep
    @Delegate
    final TestCaseRunContext context
    //private static final Closure<Closure<Boolean>> withName = { String name -> return { it.name() == name } }

    Assertions(HttpTestStep testStep, TestCaseRunContext context) {
        this.testStep = testStep
        this.context = context
    }

    abstract def getResponse()

    void statusCode(int code) {
        int statusCode = testStep.statusCode
        assert statusCode == code, println(statusCode)
        "Expected status code: ${code}, actual: ${statusCode}"
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
