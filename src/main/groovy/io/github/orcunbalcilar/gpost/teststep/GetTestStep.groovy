package io.github.orcunbalcilar.gpost.teststep

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.TestItemStatus
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.request.GetRequest
import io.github.orcunbalcilar.gpost.teststep.request.auth.HasAuth
import io.github.orcunbalcilar.gpost.teststep.response.Assertions
import io.github.orcunbalcilar.gpost.teststep.response.JsonAssertions
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient
import org.apache.hc.client5.http.protocol.HttpClientContext
import org.apache.hc.core5.http.Method

import java.util.concurrent.Future

@CompileStatic
@Slf4j
class GetTestStep extends HttpTestStep implements HasAuth {

    GetTestStep(TestCaseRunContext context, Closure<Void> body) { super(context, body) }

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
    void assertions(@DelegatesTo(value = Assertions, strategy = Closure.DELEGATE_ONLY) Closure<Void> closure) {
        Assertions assertions = new JsonAssertions(this, context)
        closure.delegate = assertions
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        this.assertable = closure
    }

    @Override
    void run() {
        CloseableHttpAsyncClient closeableHttpAsyncClient = getCloseableHttpAsyncClient()
        try {
            String testStepName = getName()
            // Create a GET request
            Map<String, String> parameters = ((GetRequest) request).getParams()
            if (parameters) {
                endpoint += "?"
                parameters.each { key, value ->
                    endpoint += key + "=" + value + "&"
                }
            }
            log.info("Running: " + name + " - " + endpoint)
            SimpleHttpRequest get = new SimpleHttpRequest(method, new URI(endpoint))
            addHeaders(get)
            HttpClientContext httpClientContext = getHttpClientContext()
            // Send request
            Future<SimpleHttpResponse> futureResponse = closeableHttpAsyncClient.execute(get, httpClientContext, getFutureCallback())
            this.response = futureResponse.get().bodyText
            log.info("Response: " + response)
            assertable.run()
            setStatus(TestItemStatus.PASSED)
        } catch (Exception | Error e) {
            e.printStackTrace()
            setStatus(TestItemStatus.FAILED)
        } finally {
            log.info("$name -> ${status.toString()}")
            closeableHttpAsyncClient.close()
        }
    }
}
