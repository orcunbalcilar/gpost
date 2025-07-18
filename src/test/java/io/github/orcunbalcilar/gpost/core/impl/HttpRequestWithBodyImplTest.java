package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.HttpRequestWithBody;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestWithBodyImplTest {
    
    private TestCaseRunContext context;
    private HttpRequestWithBodyImpl request;
    
    @BeforeEach
    void setUp() {
        context = new TestCaseRunContextImpl();
        request = new HttpRequestWithBodyImpl(context);
    }
    
    @Test
    void testExtendsHttpRequest() {
        assertSame(context, request.getContext());
        assertTrue(request.getHeaders().isEmpty());
    }
    
    @Test
    void testJsonBody() {
        String jsonContent = "{\"name\": \"John\"}";
        HttpRequestWithBody result = request.jsonBody(jsonContent);
        
        assertSame(request, result);
        // The body should be set in the underlying HttpBodyImpl
        assertNotNull(request.getBody());
    }
    
    @Test
    void testXmlBody() {
        String xmlContent = "<name>John</name>";
        HttpRequestWithBody result = request.xmlBody(xmlContent);
        
        assertSame(request, result);
        assertNotNull(request.getBody());
    }
    
    @Test
    void testTextBody() {
        String textContent = "Plain text";
        HttpRequestWithBody result = request.textBody(textContent);
        
        assertSame(request, result);
        assertNotNull(request.getBody());
    }
    
    @Test
    void testBodyConfiguration() {
        request.body(body -> {
            body.json("{\"test\": true}");
        });
        
        assertNotNull(request.getBody());
    }
    
    @Test
    void testRequestBodyConfiguration() {
        request.requestBody(builder -> {
            builder.json("{\"configured\": true}");
        });
        
        // The method should not throw an exception
        assertNotNull(request.getBody());
    }
    
    @Test
    void testInitialRequestBodyIsNull() {
        assertNull(request.getRequestBody());
    }
    
    @Test
    void testSetRequestBody() {
        TestCaseRunContext bodyContext = new TestCaseRunContextImpl();
        RequestBodyImpl body = new RequestBodyImpl(bodyContext, "test content", "application/json");
        
        request.setRequestBody(body);
        assertSame(body, request.getRequestBody());
    }
    
    @Test
    void testMethodChaining() {
        request.header("Content-Type", "application/json");
        HttpRequestWithBody result = request.jsonBody("{\"test\": true}");
        request.header("Authorization", "Bearer token");
        
        assertSame(request, result);
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
        assertEquals("Bearer token", request.getHeaders().get("Authorization"));
    }
    
    @Test
    void testBodyAndHeaders() {
        request.jsonBody("{\"name\": \"test\"}")
               .header("Authorization", "Bearer token123");
        
        assertEquals("Bearer token123", request.getHeaders().get("Authorization"));
        assertNotNull(request.getBody());
    }
    
    @Test
    void testMultipleBodyCalls() {
        request.jsonBody("{\"first\": true}");
        request.xmlBody("<second>true</second>");
        
        // Should not throw exception, last call should be effective
        assertNotNull(request.getBody());
    }
    
    @Test
    void testBasicAuthInheritance() {
        request.basicAuth("user", "pass");
        
        String authHeader = request.getHeaders().get("Authorization");
        assertNotNull(authHeader);
        assertTrue(authHeader.startsWith("Basic "));
    }
}