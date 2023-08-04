package orcun.balcilar.odapi.testcases.impl;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.util.Map;

public class HttpPostTestStep {
  private static final CloseableHttpAsyncClient asyncHttpClient = createAsyncHttpClient();

  private static CloseableHttpAsyncClient createAsyncHttpClient() {
    CloseableHttpAsyncClient asyncHttpClient =
        HttpAsyncClients.custom()
            .setDefaultRequestConfig(
                RequestConfig.custom()
                    .setConnectionRequestTimeout(Timeout.ofMinutes(1))
                    .setResponseTimeout(Timeout.ofMinutes(1))
                    .build())
            .build();
    asyncHttpClient.start();
    return asyncHttpClient;
  }

  public void post(
      String url, Map<String, String> headers, String body, FutureCallback<HttpResponse> callback)
      throws IOException {
    HttpPost request = new HttpPost(url);

    // Set headers
    if (headers != null) {
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        request.addHeader(entry.getKey(), entry.getValue());
      }
    }

    // Set body
    if (body != null) {
      request.setEntity(new StringEntity(body));
    }

    // Execute request asynchronously and set callback
    // Future<SimpleHttpResponse> futureResponse = asyncHttpClient.execute(request, callback);
  }

  public void shutdown() throws IOException {
    asyncHttpClient.close();
  }
}
