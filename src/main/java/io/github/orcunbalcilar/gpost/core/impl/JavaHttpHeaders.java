package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.HttpHeaders;
import java.util.Map;

/**
 * Java implementation of HttpHeaders.
 */
public class JavaHttpHeaders implements HttpHeaders {
    
    private final Map<String, String> headers;
    
    public JavaHttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    
    @Override
    public HttpHeaders accept(String accept) {
        headers.put("Accept", accept);
        return this;
    }
    
    @Override
    public HttpHeaders contentType(String contentType) {
        headers.put("Content-Type", contentType);
        return this;
    }
    
    @Override
    public HttpHeaders authorization(String authorization) {
        headers.put("Authorization", authorization);
        return this;
    }
    
    @Override
    public HttpHeaders header(String name, String value) {
        headers.put(name, value);
        return this;
    }
}