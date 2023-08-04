package orcun.balcilar.odapi.testcases.impl

import groovy.transform.CompileStatic

@CompileStatic
class TestStepBuilder {
    static TestStep post(@DelegatesTo(value = SoapRequestTestStep, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        SoapRequestTestStep soapRequestTestStep = new SoapRequestTestStep(new TestCaseRunContext())
        closure.delegate = soapRequestTestStep
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
        return soapRequestTestStep
    }
}
