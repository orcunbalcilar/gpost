package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.HttpBody;
import io.github.orcunbalcilar.gpost.core.HttpRequestWithBody;
import java.util.function.Consumer;

/**
 * Java implementation of HttpRequestWithBody.
 */
public class HttpRequestWithBodyImpl extends HttpRequestImpl implements HttpRequestWithBody {
    
    private final HttpBodyImpl httpBody = new HttpBodyImpl();
    
    @Override
    public HttpRequestWithBody body(Consumer<HttpBody> config) {
        config.accept(httpBody);
        return this;
    }
    
    @Override
    public HttpRequestWithBody jsonBody(String json) {
        httpBody.json(json);
        return this;
    }
    
    @Override
    public HttpRequestWithBody xmlBody(String xml) {
        httpBody.xml(xml);
        return this;
    }
    
    @Override
    public HttpRequestWithBody textBody(String text) {
        httpBody.text(text);
        return this;
    }
    
    /**
     * Get the configured body.
     */
    public HttpBodyImpl getBody() {
        return httpBody;
    }
}