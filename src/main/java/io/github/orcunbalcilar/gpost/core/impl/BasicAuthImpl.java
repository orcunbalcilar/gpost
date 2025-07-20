package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.BasicAuth;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of BasicAuth.
 */
public class BasicAuthImpl implements BasicAuth {
    
    private static final Logger logger = LoggerFactory.getLogger(BasicAuthImpl.class);
    
    private String username;
    private String password;
    
    @Override
    public BasicAuth username(String username) {
        this.username = username;
        return this;
    }
    
    @Override
    public BasicAuth password(String password) {
        this.password = password;
        return this;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getType() {
        return "basic";
    }
    
    @Override
    public void apply(Object request) {
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            // This would be applied to the actual HTTP request
            // Implementation depends on the HTTP client being used
            logger.debug("Applying Basic Auth: {}", encodedCredentials);
        }
    }
}