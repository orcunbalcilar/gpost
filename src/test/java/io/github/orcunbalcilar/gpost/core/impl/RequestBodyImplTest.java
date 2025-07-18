package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.RequestBody;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import io.github.orcunbalcilar.gpost.core.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestBodyImplTest {
    
    private TestCaseRunContext context;
    private RequestBody requestBody;
    
    @BeforeEach
    void setUp() {
        context = new TestCaseRunContextImpl();
        requestBody = new RequestBodyImpl(context);
    }
    
    @Test
    void testInitialValues() {
        assertSame(context, requestBody.getContext());
        assertNull(requestBody.getContent());
        assertNull(requestBody.getContentType());
    }
    
    @Test
    void testSetAndGetContent() {
        requestBody.setContent("test content");
        assertEquals("test content", requestBody.getContent());
        assertEquals("test content", requestBody.build());
    }
    
    @Test
    void testConstructorWithContentAndType() {
        RequestBody body = new RequestBodyImpl(context, "json content", ContentType.APPLICATION_JSON);
        
        assertEquals("json content", body.getContent());
        assertEquals(ContentType.APPLICATION_JSON, body.getContentType());
        assertEquals("json content", body.build());
    }
    
    @Test
    void testContentTypeConstants() {
        assertEquals("application/json", ContentType.APPLICATION_JSON);
        assertEquals("application/xml", ContentType.APPLICATION_XML);
        assertEquals("text/plain", ContentType.TEXT_PLAIN);
        assertEquals("text/html", ContentType.TEXT_HTML);
        assertEquals("application/x-www-form-urlencoded", ContentType.APPLICATION_FORM_URLENCODED);
        assertEquals("multipart/form-data", ContentType.MULTIPART_FORM_DATA);
        assertEquals("text/xml; charset=utf-8", ContentType.TEXT_XML_SOAP);
    }
    
    @Test
    void testOverwriteContent() {
        requestBody.setContent("original content");
        assertEquals("original content", requestBody.getContent());
        
        requestBody.setContent("new content");
        assertEquals("new content", requestBody.getContent());
    }
    
    @Test
    void testNullContent() {
        requestBody.setContent(null);
        assertNull(requestBody.getContent());
        assertNull(requestBody.build());
    }
    
    @Test
    void testEmptyContent() {
        requestBody.setContent("");
        assertEquals("", requestBody.getContent());
        assertEquals("", requestBody.build());
    }
}