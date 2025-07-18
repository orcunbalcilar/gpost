package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.RequestBody;
import io.github.orcunbalcilar.gpost.core.RequestBodyBuilder;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import io.github.orcunbalcilar.gpost.core.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestBodyBuilderImplTest {
    
    private TestCaseRunContext context;
    private RequestBodyBuilder builder;
    
    @BeforeEach
    void setUp() {
        context = new TestCaseRunContextImpl();
        builder = new RequestBodyBuilderImpl(context);
    }
    
    @Test
    void testJsonBody() {
        String jsonContent = "{\"name\": \"John\", \"age\": 30}";
        RequestBody body = builder.json(jsonContent);
        
        assertEquals(jsonContent, body.getContent());
        assertEquals(ContentType.APPLICATION_JSON, body.getContentType());
        assertEquals(jsonContent, body.build());
        assertSame(context, body.getContext());
    }
    
    @Test
    void testXmlBody() {
        String xmlContent = "<person><name>John</name><age>30</age></person>";
        RequestBody body = builder.xml(xmlContent);
        
        assertEquals(xmlContent, body.getContent());
        assertEquals(ContentType.APPLICATION_XML, body.getContentType());
        assertEquals(xmlContent, body.build());
        assertSame(context, body.getContext());
    }
    
    @Test
    void testTextBody() {
        String textContent = "Plain text content";
        RequestBody body = builder.text(textContent);
        
        assertEquals(textContent, body.getContent());
        assertEquals(ContentType.TEXT_PLAIN, body.getContentType());
        assertEquals(textContent, body.build());
        assertSame(context, body.getContext());
    }
    
    @Test
    void testFormBody() {
        String formContent = "name=John&age=30";
        RequestBody body = builder.form(formContent);
        
        assertEquals(formContent, body.getContent());
        assertEquals(ContentType.APPLICATION_FORM_URLENCODED, body.getContentType());
        assertEquals(formContent, body.build());
        assertSame(context, body.getContext());
    }
    
    @Test
    void testCustomBody() {
        String customContent = "custom content";
        String customContentType = "application/custom";
        RequestBody body = builder.custom(customContentType, customContent);
        
        assertEquals(customContent, body.getContent());
        assertEquals(customContentType, body.getContentType());
        assertEquals(customContent, body.build());
        assertSame(context, body.getContext());
    }
    
    @Test
    void testEmptyContent() {
        RequestBody body = builder.json("");
        assertEquals("", body.getContent());
        assertEquals(ContentType.APPLICATION_JSON, body.getContentType());
    }
    
    @Test
    void testNullContent() {
        RequestBody body = builder.json(null);
        assertNull(body.getContent());
        assertEquals(ContentType.APPLICATION_JSON, body.getContentType());
    }
    
    @Test
    void testMultipleCallsCreateNewBodies() {
        RequestBody body1 = builder.json("content1");
        RequestBody body2 = builder.json("content2");
        
        assertNotSame(body1, body2);
        assertEquals("content1", body1.getContent());
        assertEquals("content2", body2.getContent());
    }
}