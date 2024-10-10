package io.github.orcunbalcilar.gpost.teststep

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import org.apache.hc.core5.http.Method

@CompileStatic
@Slf4j
class PutTestStep extends RequestBodyTestStep {

    PutTestStep(TestCaseRunContext context, Closure closure) { super(context, closure) }

    @Override
    Method getMethod() { Method.PUT }
}
