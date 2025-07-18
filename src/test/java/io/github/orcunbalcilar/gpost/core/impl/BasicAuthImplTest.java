package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.BasicAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicAuthImplTest {
    
    private BasicAuth basicAuth;
    
    @BeforeEach
    void setUp() {
        basicAuth = new BasicAuthImpl();
    }
    
    @Test
    void testUsernameAndPassword() {
        basicAuth.username("testUser").password("testPass");
        
        assertEquals("testUser", basicAuth.getUsername());
        assertEquals("testPass", basicAuth.getPassword());
    }
    
    @Test
    void testGetType() {
        assertEquals("basic", basicAuth.getType());
    }
    
    @Test
    void testMethodChaining() {
        BasicAuth result = basicAuth.username("user");
        assertSame(basicAuth, result);
        
        result = basicAuth.password("pass");
        assertSame(basicAuth, result);
    }
    
    @Test
    void testInitialValues() {
        assertNull(basicAuth.getUsername());
        assertNull(basicAuth.getPassword());
    }
    
    @Test
    void testApplyMethod() {
        basicAuth.username("user").password("pass");
        
        // Test that apply method doesn't throw exception
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
    }
    
    @Test
    void testApplyWithNullCredentials() {
        // Test that apply with null credentials doesn't throw exception
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
    }
    
    @Test
    void testOverwriteCredentials() {
        basicAuth.username("user1").password("pass1");
        assertEquals("user1", basicAuth.getUsername());
        assertEquals("pass1", basicAuth.getPassword());
        
        basicAuth.username("user2").password("pass2");
        assertEquals("user2", basicAuth.getUsername());
        assertEquals("pass2", basicAuth.getPassword());
    }
}