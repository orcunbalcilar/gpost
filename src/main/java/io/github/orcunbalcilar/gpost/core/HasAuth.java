package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for authentication support.
 * Provides methods to configure authentication for HTTP requests.
 */
public interface HasAuth {
    
    /**
     * Configure basic authentication.
     * 
     * @param config The basic authentication configuration
     * @return The object implementing HasAuth for method chaining
     */
    HasAuth basicAuth(Consumer<BasicAuth> config);
    
    /**
     * Get the configured authentication.
     * 
     * @return The authentication or null if not configured
     */
    Auth getAuth();
}