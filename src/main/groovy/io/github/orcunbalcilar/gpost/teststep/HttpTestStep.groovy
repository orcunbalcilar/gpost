package io.github.orcunbalcilar.gpost.teststep

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import io.github.orcunbalcilar.gpost.teststep.request.Request
import io.github.orcunbalcilar.gpost.teststep.request.auth.HasAuth
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse
import org.apache.hc.client5.http.auth.AuthScope
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient
import org.apache.hc.client5.http.impl.async.HttpAsyncClients
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider
import org.apache.hc.client5.http.protocol.HttpClientContext
import org.apache.hc.core5.concurrent.FutureCallback
import org.apache.hc.core5.http.HttpHost
import org.apache.hc.core5.http.Method
import org.apache.hc.core5.util.Timeout

@CompileStatic
@Slf4j
abstract class HttpTestStep extends TestStep implements HasAuth {
    @Delegate
    protected final TestCaseRunContext context

    protected Closure<Void> assertable

    Request request

    String endpoint
    int connectionTimeout = 60000
    int timeout = 60000
    int statusCode
    String response

    abstract void request(Closure closure)

    abstract Method getMethod()

    abstract void assertions(Closure<Void> closure)

    Closure<Void> getAssertable() {
        assertable ?: { assert statusCode == 200, "Expected status code: 200, actual: ${statusCode}" }
    }

    HttpTestStep(TestCaseRunContext context, Closure<Void> body) {
        this.context = context
    }

    void timeout(int timeout) { this.timeout = timeout }

    void url(String url) { this.endpoint = url }

    protected void addHeaders(SimpleHttpRequest post) {
        Map<String, String> headers = request.headers()
        if (!headers.isEmpty()) {
            headers.each { key, value -> post.addHeader(key, value) }
        }
    }

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
        credentialsProvider.setCredentials(new AuthScope(new HttpHost("https", endpoint)), auth.getCredentials())
        httpClientContext.setCredentialsProvider(credentialsProvider)
        httpClientContext
    }

    protected FutureCallback<SimpleHttpResponse> getFutureCallback() {
        new FutureCallback<SimpleHttpResponse>() {
            @Override
            void completed(SimpleHttpResponse result) {
                response = result.bodyText
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

    String getName() { Object.name ?: "$method-${endpoint.split("/").last()}" }
}
