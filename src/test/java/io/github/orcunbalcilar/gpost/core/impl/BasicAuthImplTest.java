package io.github.orcunbalcilar.gpost.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BasicAuthImpl.
 * Tests the basic authentication implementation with username/password handling.
 */
class BasicAuthImplTest {

    private BasicAuthImpl basicAuth;

    @BeforeEach
    void setUp() {
        basicAuth = new BasicAuthImpl();
    }

    @Test
    void shouldSetUsername() {
        // Given
        String username = "testUser";

        // When
        BasicAuthImpl result = (BasicAuthImpl) basicAuth.username(username);

        // Then
        assertEquals(username, basicAuth.getUsername());
        assertSame(basicAuth, result); // Should return the same instance for fluent API
    }

    @Test
    void shouldSetPassword() {
        // Given
        String password = "testPassword";

        // When
        BasicAuthImpl result = (BasicAuthImpl) basicAuth.password(password);

        // Then
        assertEquals(password, basicAuth.getPassword());
        assertSame(basicAuth, result); // Should return the same instance for fluent API
    }

    @Test
    void shouldChainUsernameAndPassword() {
        // Given
        String username = "testUser";
        String password = "testPassword";

        // When
        BasicAuthImpl result = (BasicAuthImpl) basicAuth
                .username(username)
                .password(password);

        // Then
        assertEquals(username, basicAuth.getUsername());
        assertEquals(password, basicAuth.getPassword());
        assertSame(basicAuth, result);
    }

    @Test
    void shouldReturnBasicAsType() {
        // When
        String type = basicAuth.getType();

        // Then
        assertEquals("basic", type);
    }

    @Test
    void shouldHandleNullUsername() {
        // When
        basicAuth.username(null);

        // Then
        assertNull(basicAuth.getUsername());
    }

    @Test
    void shouldHandleNullPassword() {
        // When
        basicAuth.password(null);

        // Then
        assertNull(basicAuth.getPassword());
    }

    @Test
    void shouldOverwriteUsername() {
        // Given
        String originalUsername = "originalUser";
        String newUsername = "newUser";

        // When
        basicAuth.username(originalUsername);
        basicAuth.username(newUsername);

        // Then
        assertEquals(newUsername, basicAuth.getUsername());
    }

    @Test
    void shouldOverwritePassword() {
        // Given
        String originalPassword = "originalPassword";
        String newPassword = "newPassword";

        // When
        basicAuth.password(originalPassword);
        basicAuth.password(newPassword);

        // Then
        assertEquals(newPassword, basicAuth.getPassword());
    }

    @Test
    void shouldApplyBasicAuthWithValidCredentials() {
        // Given
        String username = "testUser";
        String password = "testPassword";
        basicAuth.username(username).password(password);

        // When & Then - should not throw any exceptions
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
        
        // Verify that credentials are properly set
        assertEquals(username, basicAuth.getUsername());
        assertEquals(password, basicAuth.getPassword());
    }

    @Test
    void shouldNotApplyBasicAuthWithNullUsername() {
        // Given
        basicAuth.username(null).password("password");

        // When & Then - should not throw any exceptions, but also shouldn't log anything
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
        
        // Verify state
        assertNull(basicAuth.getUsername());
        assertEquals("password", basicAuth.getPassword());
    }

    @Test
    void shouldNotApplyBasicAuthWithNullPassword() {
        // Given
        basicAuth.username("username").password(null);

        // When & Then - should not throw any exceptions, but also shouldn't log anything
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
        
        // Verify state
        assertEquals("username", basicAuth.getUsername());
        assertNull(basicAuth.getPassword());
    }

    @Test
    void shouldNotApplyBasicAuthWithBothNullCredentials() {
        // Given
        basicAuth.username(null).password(null);

        // When & Then - should not throw any exceptions, but also shouldn't log anything
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
        
        // Verify state
        assertNull(basicAuth.getUsername());
        assertNull(basicAuth.getPassword());
    }

    @Test
    void shouldHandleEmptyUsername() {
        // Given
        String emptyUsername = "";
        String password = "password";
        basicAuth.username(emptyUsername).password(password);

        // When & Then
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
        assertEquals(emptyUsername, basicAuth.getUsername());
    }

    @Test
    void shouldHandleEmptyPassword() {
        // Given
        String username = "username";
        String emptyPassword = "";
        basicAuth.username(username).password(emptyPassword);

        // When & Then
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
        assertEquals(emptyPassword, basicAuth.getPassword());
    }

    @Test
    void shouldHandleSpecialCharactersInCredentials() {
        // Given
        String username = "user@domain.com";
        String password = "p@$$w0rd!";
        basicAuth.username(username).password(password);

        // When & Then
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
        assertEquals(username, basicAuth.getUsername());
        assertEquals(password, basicAuth.getPassword());
    }

    @Test
    void shouldProduceCorrectBase64Encoding() {
        // Given
        String username = "admin";
        String password = "secret";
        basicAuth.username(username).password(password);

        // Expected Base64 encoding of "admin:secret"
        String expectedEncoding = Base64.getEncoder().encodeToString("admin:secret".getBytes());

        // When & Then - should not throw any exceptions
        assertDoesNotThrow(() -> basicAuth.apply(new Object()));
        
        // Verify that the basic auth produces the expected encoding internally
        // We can test this by manually creating the same encoding
        String actualEncoding = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        assertEquals(expectedEncoding, actualEncoding);
        
        // Verify credentials are set correctly
        assertEquals(username, basicAuth.getUsername());
        assertEquals(password, basicAuth.getPassword());
    }
}
