package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.RequestBody;
import io.github.orcunbalcilar.gpost.core.RequestBodyBuilder;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import io.github.orcunbalcilar.gpost.core.ContentType;

/**
 * Implementation of RequestBodyBuilder.
 */
public class RequestBodyBuilderImpl implements RequestBodyBuilder {
    
    private final TestCaseRunContext context;
    
    public RequestBodyBuilderImpl(TestCaseRunContext context) {
        this.context = context;
    }
    
    @Override
    public RequestBody json(String jsonContent) {
        return new RequestBodyImpl(context, jsonContent, ContentType.APPLICATION_JSON);
    }
    
    @Override
    public RequestBody xml(String xmlContent) {
        return new RequestBodyImpl(context, xmlContent, ContentType.APPLICATION_XML);
    }
    
    @Override
    public RequestBody text(String textContent) {
        return new RequestBodyImpl(context, textContent, ContentType.TEXT_PLAIN);
    }
    
    @Override
    public RequestBody form(String formContent) {
        return new RequestBodyImpl(context, formContent, ContentType.APPLICATION_FORM_URLENCODED);
    }
    
    @Override
    public RequestBody custom(String contentType, String content) {
        return new RequestBodyImpl(context, content, contentType);
    }
}