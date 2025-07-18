package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.HttpRequest;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestImplTest {
    
    private TestCaseRunContext context;
    private HttpRequestImpl request;
    
    @BeforeEach
    void setUp() {
        context = new TestCaseRunContextImpl();
        request = new HttpRequestImpl(context);
    }
    
    @Test
    void testContext() {
        assertSame(context, request.getContext());
    }
    
    @Test
    void testInitialHeaders() {
        assertTrue(request.getHeaders().isEmpty());
    }
    
    @Test
    void testSetHeader() {
        HttpRequest result = request.header("Content-Type", "application/json");
        
        assertSame(request, result);
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
    }
    
    @Test
    void testMultipleHeaders() {
        request.header("Content-Type", "application/json")
               .header("Authorization", "Bearer token123");
        
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
        assertEquals("Bearer token123", request.getHeaders().get("Authorization"));
    }
    
    @Test
    void testBasicAuth() {
        request.basicAuth("user", "pass");
        
        String authHeader = request.getHeaders().get("Authorization");
        assertNotNull(authHeader);
        assertTrue(authHeader.startsWith("Basic "));
    }
    
    @Test
    void testBearerAuth() {
        String token = "abc123";
        request.bearerAuth(token);
        
        assertEquals("Bearer " + token, request.getHeaders().get("Authorization"));
    }
    
    @Test
    void testHeadersConfiguration() {
        request.headers(headers -> {
            headers.contentType("application/json");
            headers.accept("application/json");
        });
        
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
        assertEquals("application/json", request.getHeaders().get("Accept"));
    }
    
    @Test
    void testOverwriteHeader() {
        request.header("Content-Type", "application/xml");
        assertEquals("application/xml", request.getHeaders().get("Content-Type"));
        
        request.header("Content-Type", "application/json");
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
    }
    
    @Test
    void testNullHeaderValue() {
        request.header("Custom-Header", null);
        assertNull(request.getHeaders().get("Custom-Header"));
    }
    
    @Test
    void testEmptyHeaderValue() {
        request.header("Custom-Header", "");
        assertEquals("", request.getHeaders().get("Custom-Header"));
    }
    
    @Test
    void testBasicAuthConfiguration() {
        request.basicAuth(basicAuth -> {
            basicAuth.username("testuser");
            basicAuth.password("testpass");
        });
        
        assertNotNull(request.getAuth());
        assertEquals("basic", request.getAuth().getType());
    }
    
    @Test
    void testInitialAuthIsNull() {
        assertNull(request.getAuth());
    }
}