package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for SOAP body configuration.
 */
public interface SoapBody {
    
    /**
     * Set the SOAP envelope.
     * 
     * @param envelope The SOAP envelope
     * @return This SOAP body for method chaining
     */
    SoapBody envelope(String envelope);
    
    /**
     * Set the SOAP namespace.
     * 
     * @param namespace The SOAP namespace
     * @return This SOAP body for method chaining
     */
    SoapBody namespace(String namespace);
    
    /**
     * Set the SOAP action.
     * 
     * @param action The SOAP action
     * @return This SOAP body for method chaining
     */
    SoapBody action(String action);
}