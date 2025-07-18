package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.HttpHeaders;
import io.github.orcunbalcilar.gpost.core.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Java implementation of HttpRequest.
 */
public class HttpRequestImpl implements HttpRequest {
    
    protected final Map<String, String> headers = new HashMap<>();
    protected final HttpHeadersImpl httpHeaders = new HttpHeadersImpl(headers);
    
    @Override
    public HttpRequest headers(Consumer<HttpHeaders> config) {
        config.accept(httpHeaders);
        return this;
    }
    
    @Override
    public HttpRequest header(String name, String value) {
        headers.put(name, value);
        return this;
    }
    
    @Override
    public HttpRequest basicAuth(String username, String password) {
        String auth = username + ":" + password;
        String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
        headers.put("Authorization", "Basic " + encodedAuth);
        return this;
    }
    
    @Override
    public HttpRequest bearerAuth(String token) {
        headers.put("Authorization", "Bearer " + token);
        return this;
    }
    
    /**
     * Get the configured headers.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }
    
    /**
     * Get the HTTP headers object.
     */
    public HttpHeadersImpl getHttpHeaders() {
        return httpHeaders;
    }
}