package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.SoapBody;

/**
 * Java implementation of SoapBody.
 */
public class JavaSoapBody implements SoapBody {
    
    private String envelope;
    private String namespace;
    private String action;
    
    @Override
    public SoapBody envelope(String envelope) {
        this.envelope = envelope;
        return this;
    }
    
    @Override
    public SoapBody namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }
    
    @Override
    public SoapBody action(String action) {
        this.action = action;
        return this;
    }
    
    /**
     * Get the SOAP content.
     */
    public String getContent() {
        if (envelope != null) {
            return envelope;
        }
        
        // Build a simple SOAP envelope if individual components are provided
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"");
        if (namespace != null) {
            sb.append(" xmlns:ns=\"").append(namespace).append("\"");
        }
        sb.append(">");
        sb.append("<soap:Body>");
        if (action != null) {
            sb.append("<ns:").append(action).append(">");
            sb.append("</ns:").append(action).append(">");
        }
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        
        return sb.toString();
    }
}