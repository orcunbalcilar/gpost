package io.github.orcunbalcilar.gpost.teststep

import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.TestItemStatus
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.request.PostRequest
import io.github.orcunbalcilar.gpost.teststep.response.Assertions
import io.github.orcunbalcilar.gpost.teststep.response.JsonAssertions
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient
import org.apache.hc.client5.http.protocol.HttpClientContext
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.Method

import java.util.concurrent.Future

@CompileStatic
@Slf4j
class PostTestStep extends HttpTestStep {

    PostTestStep(TestCaseRunContext context, Closure<Void> body) { super(context, body) }

    @Override
    Method getMethod() { Method.POST }

    @Override
    void request(@DelegatesTo(value = PostRequest, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.request = new PostRequest(context)
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
            log.info("Running test step: " + testStepName)
            SimpleHttpRequest post = new SimpleHttpRequest(method, new URI(endpoint))
            addHeaders(post)
            log.info("Request body: " + ((PostRequest) request).bodyContent)
            //log.info("Request headers: " + post.getHeaders())
            post.setBody(((PostRequest) request).bodyContent, ContentType.APPLICATION_JSON)
            HttpClientContext httpClientContext = getHttpClientContext()
            // Send request
            Future<SimpleHttpResponse> futureResponse = closeableHttpAsyncClient.execute(post, httpClientContext, getFutureCallback())
            this.response = futureResponse.get().bodyText
            //log.info("Response: " + JsonOutput.prettyPrint(response))
            assertable.run()
            setStatus(TestItemStatus.PASSED)
        } catch (Exception | Error e) {
            e.printStackTrace()
            log.error(e.asString())
            log.info("Response: " + JsonOutput.prettyPrint(response))
            setStatus(TestItemStatus.FAILED)
        } finally {
            log.info("$name : ${status.toString()}")
            closeableHttpAsyncClient.close()
        }
    }
}
