package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.BasicAuth;
import java.util.Base64;

/**
 * Implementation of BasicAuth.
 */
public class BasicAuthImpl implements BasicAuth {
    
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
            System.out.println("Applying Basic Auth: " + encodedCredentials);
        }
    }
}