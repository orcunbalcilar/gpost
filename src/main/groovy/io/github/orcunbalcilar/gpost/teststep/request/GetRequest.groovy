package io.github.orcunbalcilar.gpost.teststep.request

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
class GetRequest extends Request {
    private final Map<String, String> allParams = [:]

    GetRequest(TestCaseRunContext context) {
        super(context)
    }

    void getParams(Map<String, String> map) {
        allParams << map
    }

    Map<String, String> getParams() { allParams }
}
