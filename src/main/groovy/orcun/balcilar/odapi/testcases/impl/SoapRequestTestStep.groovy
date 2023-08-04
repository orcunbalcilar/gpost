package orcun.balcilar.odapi.testcases.impl

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j2
import groovy.xml.XmlUtil
import orcun.balcilar.odapi.auth.HasAuth
import org.apache.hc.client5.http.ContextBuilder
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse
import org.apache.hc.client5.http.auth.AuthScope
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials
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
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError

import java.net.http.HttpResponse
import java.util.concurrent.Future

@Log4j2
@CompileStatic
class SoapRequestTestStep extends TestStep implements HasAuth {
    private String uri
    private Map headers = [:]
    private String request
    private int timeout = 60000
    private HttpResponse<String> httpResponse
    private int statusCode
    private String response
    private Closure<Void> assertions
    @Delegate
    private final TestCaseRunContext context

    SoapRequestTestStep(TestCaseRunContext context) {
        this.context = context
    }

    void name(String name) { this.name = name }

    void to(String uri) { this.uri = uri }

    void header(Map headers) { this.headers = headers }

    void request(String request) { this.request = request }

    void assertions(@DelegatesTo(value = Assertions, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        this.assertions = closure
    }

    int getStatusCode() { statusCode }

    String getResponse() { response }

    @Override
    void run() {
        try {
            log.info("Running test step: " + name)
            CloseableHttpAsyncClient asyncHttpClient =
                    HttpAsyncClients.custom()
                            .setDefaultRequestConfig(RequestConfig.custom()
                                    .setConnectionRequestTimeout(Timeout.ofMinutes(1))
                                    .setResponseTimeout(Timeout.ofMinutes(1))
                                    .build())
                            .build();
            asyncHttpClient.start();
            SimpleHttpRequest post = new SimpleHttpRequest(Method.POST, new URI(uri))
            // Set headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // Set body
            if (post != null) {
                post.setBody(request, ContentType.TEXT_XML)
            }
            HttpClientContext localContext = ContextBuilder.create().useCredentialsProvider(new BasicCredentialsProvider())
                    .preemptiveBasicAuth(new HttpHost("https", "wspreprod01.thy.com"),
                            new UsernamePasswordCredentials("ykmwstestuser", "wsc.Ob16+".toCharArray()))
                    .build();
            // Send request
            HttpClientContext httpClientContext = HttpClientContext.create();
            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(new AuthScope(new HttpHost("https", "wspreprod01.thy.com")), new UsernamePasswordCredentials("ykmwstestuser", "wsc.Ob16+".toCharArray()))
            httpClientContext.setCredentialsProvider(credentialsProvider);
            Future<SimpleHttpResponse> futureResponse = asyncHttpClient.execute(post, httpClientContext, new FutureCallback<SimpleHttpResponse>() {
                @Override
                void completed(SimpleHttpResponse result) {
                    response = result.bodyText
                    statusCode = result.getCode()
                    log.info("Request completed")
                }

                @Override
                void failed(Exception ex) {
                    log.error(ex.toString())
                }

                @Override
                void cancelled() {
                    log.error("Request cancelled")
                }
            });
            futureResponse.get()
            log.info("Test step is finished: " + name)
            assertions.delegate = new Assertions(this, context)
            //log.info("Response: " + XmlUtil.serialize(response))
            assertions.run()
            setStatus(TestItemStatus.PASSED)
        } catch (Exception | PowerAssertionError e) {
            e.printStackTrace()
            setStatus(TestItemStatus.FAILED)
            log.info(getStatus().toString())
        }
    }
}
