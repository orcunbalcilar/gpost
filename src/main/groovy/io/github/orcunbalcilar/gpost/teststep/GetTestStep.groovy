package io.github.orcunbalcilar.gpost.teststep

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.request.GetRequest
import org.apache.hc.core5.http.Method

@CompileStatic
@Slf4j
class GetTestStep extends HttpTestStep {

    final Closure closure

    GetTestStep(TestCaseRunContext context, Closure closure) { super(context, closure) }

    @Override
    Method getMethod() { Method.GET }

    @Override
    void request(@DelegatesTo(value = GetRequest, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.request = new GetRequest(context)
        closure.delegate = request
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
    }

    @Override
    String getEndpoint() {
        String endpoint = super.getEndpoint()
        Map<String, String> parameters = ((GetRequest) request).getParams()
        if (parameters) {
            StringBuilder endpointBuilder = new StringBuilder(endpoint)
            endpointBuilder.append("?")
            endpointBuilder.append(parameters.collect { key, value -> "$key=$value"
            }.join("&"))
            return endpointBuilder.toString()
        }
        endpoint
    }

}
