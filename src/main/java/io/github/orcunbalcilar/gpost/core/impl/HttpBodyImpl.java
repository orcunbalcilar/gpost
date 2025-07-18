package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.HttpBody;
import io.github.orcunbalcilar.gpost.core.SoapBody;
import java.util.function.Consumer;

/**
 * Java implementation of HttpBody.
 */
public class HttpBodyImpl implements HttpBody {
    
    private String content;
    private String contentType;
    private final SoapBodyImpl soapBody = new SoapBodyImpl();
    
    @Override
    public HttpBody json(String json) {
        this.content = json;
        this.contentType = "application/json";
        return this;
    }
    
    @Override
    public HttpBody xml(String xml) {
        this.content = xml;
        this.contentType = "application/xml";
        return this;
    }
    
    @Override
    public HttpBody text(String text) {
        this.content = text;
        this.contentType = "text/plain";
        return this;
    }
    
    @Override
    public HttpBody soap(Consumer<SoapBody> config) {
        config.accept(soapBody);
        this.content = soapBody.getContent();
        this.contentType = "text/xml";
        return this;
    }
    
    /**
     * Get the content.
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Get the content type.
     */
    public String getContentType() {
        return contentType;
    }
    
    /**
     * Get the SOAP body.
     */
    public SoapBodyImpl getSoapBody() {
        return soapBody;
    }
}