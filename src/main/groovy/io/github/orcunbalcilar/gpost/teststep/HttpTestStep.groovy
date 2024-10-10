package io.github.orcunbalcilar.gpost.teststep

import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import groovy.xml.XmlUtil
import io.github.orcunbalcilar.gpost.TestItemStatus
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.request.Request
import io.github.orcunbalcilar.gpost.teststep.request.RequestWithBody
import io.github.orcunbalcilar.gpost.teststep.request.auth.HasAuth
import io.github.orcunbalcilar.gpost.teststep.response.Assertions
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse
import org.apache.hc.client5.http.auth.AuthScope
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient
import org.apache.hc.client5.http.impl.async.HttpAsyncClients
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider
import org.apache.hc.client5.http.protocol.HttpClientContext
import org.apache.hc.core5.concurrent.FutureCallback
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.HttpHost
import org.apache.hc.core5.http.Method
import org.apache.hc.core5.util.Timeout

import java.util.concurrent.Future

@CompileStatic
@Slf4j
abstract class HttpTestStep extends TestStep implements HasAuth {
    @Delegate
    final TestCaseRunContext context

    private final Closure closure

    String name

    protected Closure<Void> assertable

    Request request
    def endpoint
    int connectionTimeout = 60000
    int timeout = 60000
    int statusCode

    SimpleHttpResponse response

    HttpTestStep(TestCaseRunContext context, Closure closure) {
        this.context = context
        this.closure = closure
    }

    abstract void request(Closure closure)

    abstract Method getMethod()

    String getEndpoint() { endpoint.toString() }

    void assertions(@DelegatesTo(value = Assertions, strategy = Closure.DELEGATE_ONLY) Closure<Void> closure) {
        Assertions assertions = new Assertions(this, context)
        closure.delegate = assertions
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        this.assertable = closure
    }

    @Override
    void run() {
        closure.call(context)
        CloseableHttpAsyncClient closeableHttpAsyncClient = getCloseableHttpAsyncClient()
        try {
            String testStepName = getName()
            log.info("Running test step: " + testStepName)
            SimpleHttpRequest simpleHttpRequest = createSimpleHttpRequest()
            HttpClientContext httpClientContext = getHttpClientContext()
            // Send request
            Future<SimpleHttpResponse> futureResponse = closeableHttpAsyncClient.execute(simpleHttpRequest, httpClientContext, getFutureCallback())
            this.response = futureResponse.get()
            log.info("Response: " + prettyPrintResponse())
            assertable.run()
            setStatus(TestItemStatus.PASSED)
        } catch (Exception | Error e) {
            e.printStackTrace()
            log.error(e.asString())
            setStatus(TestItemStatus.FAILED)
        } finally {
            log.info("${getName()} : ${status.toString()}")
            closeableHttpAsyncClient.close()
        }
    }

    SimpleHttpRequest createSimpleHttpRequest() {
        SimpleHttpRequest simpleHttpRequest = new SimpleHttpRequest(method, new URI(getEndpoint()))
        log.info("Request method: " + simpleHttpRequest.getRequestUri())
        Map<String, String> headers = request.createHeaders()
        if (method != Method.GET) {
            ContentType contentType = ((RequestWithBody) request).getBody().getContentType()
            String bodyContent = ((RequestWithBody) request).bodyContent
            log.info("Request body: " + bodyContent)
            simpleHttpRequest.setBody(bodyContent, contentType)
            headers.put("Content-Type", contentType.getMimeType())
        }
        if (headers.isEmpty()) {
            throw new IllegalArgumentException("$method Headers cannot be empty")
        }
        headers.each { key, value -> simpleHttpRequest.addHeader(key, value) }
        simpleHttpRequest
    }

    private String prettyPrintResponse() {
        return response.getContentType() == ContentType.APPLICATION_JSON ? JsonOutput.prettyPrint(response.bodyText) : XmlUtil.serialize(response.bodyText)
    }

    Closure<Void> getAssertable() {
        assertable ?: { assert statusCode == 200, "Expected status code: 200, actual: ${statusCode}" }
    }

    void timeout(int timeout) { this.timeout = timeout }

    void url(String url) { this.endpoint = url }

    void url(GString url) { this.endpoint = url }

    protected CloseableHttpAsyncClient getCloseableHttpAsyncClient() {
        CloseableHttpAsyncClient asyncHttpClient =
                HttpAsyncClients.custom()
                        .setDefaultRequestConfig(RequestConfig.custom()
                                .setConnectionRequestTimeout(Timeout.ofMilliseconds(connectionTimeout))
                                .setResponseTimeout(Timeout.ofMilliseconds(timeout))
                                .build())
                        .build()
        asyncHttpClient.start()
        return asyncHttpClient
    }

    protected HttpClientContext getHttpClientContext() {
        HttpClientContext httpClientContext = HttpClientContext.create()
        if (auth == null) {
            return httpClientContext
        }
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider()
        credentialsProvider.setCredentials(new AuthScope(new HttpHost("https", getEndpoint())), auth.getCredentials())
        httpClientContext.setCredentialsProvider(credentialsProvider)
        httpClientContext
    }

    protected FutureCallback<SimpleHttpResponse> getFutureCallback() {
        new FutureCallback<SimpleHttpResponse>() {
            @Override
            void completed(SimpleHttpResponse result) {
                response = result
                statusCode = result.getCode()
            }

            @Override
            void failed(Exception ex) {
                log.error(ex.toString())
            }

            @Override
            void cancelled() {
                log.error("Request cancelled")
            }
        }
    }

    String getName() { name ?: "$method-${getEndpoint().split("/").last()}" }
}
