package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.RequestBody;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import io.github.orcunbalcilar.gpost.core.ContentType;

/**
 * Implementation of RequestBody.
 */
public class RequestBodyImpl implements RequestBody {
    
    private final TestCaseRunContext context;
    private String content;
    private String contentType;
    
    public RequestBodyImpl(TestCaseRunContext context) {
        this.context = context;
    }
    
    public RequestBodyImpl(TestCaseRunContext context, String content, String contentType) {
        this.context = context;
        this.content = content;
        this.contentType = contentType;
    }
    
    @Override
    public String getContentType() {
        return contentType;
    }
    
    @Override
    public String build() {
        return content;
    }
    
    @Override
    public String getContent() {
        return content;
    }
    
    @Override
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public TestCaseRunContext getContext() {
        return context;
    }
    
    /**
     * Set the content type.
     * 
     * @param contentType The content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}