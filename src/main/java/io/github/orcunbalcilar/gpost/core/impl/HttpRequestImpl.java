package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Java implementation of HttpRequest.
 */
public class HttpRequestImpl implements HttpRequest {
    
    protected final Map<String, String> headers = new HashMap<>();
    protected final HttpHeadersImpl httpHeaders = new HttpHeadersImpl(headers);
    protected final TestCaseRunContext context;
    protected Auth auth;
    
    public HttpRequestImpl(TestCaseRunContext context) {
        this.context = context;
    }
    
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
    
    @Override
    public HasAuth basicAuth(Consumer<BasicAuth> config) {
        BasicAuthImpl basicAuth = new BasicAuthImpl();
        config.accept(basicAuth);
        this.auth = basicAuth;
        return this;
    }
    
    @Override
    public Auth getAuth() {
        return auth;
    }
    
    @Override
    public TestCaseRunContext getContext() {
        return context;
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