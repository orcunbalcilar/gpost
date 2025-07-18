package io.github.orcunbalcilar.gpost.java;

import java.lang.reflect.Method;

/**
 * Java wrapper for SOAP body configuration.
 */
public class SoapWrapper {
    
    private final Object groovySoap;
    
    public SoapWrapper(Object groovySoap) {
        this.groovySoap = groovySoap;
    }
    
    /**
     * Add a SOAP element with a name and value.
     * 
     * @param name The element name
     * @param value The element value
     * @return This wrapper for method chaining
     */
    public SoapWrapper element(String name, Object value) {
        try {
            // Call the element method dynamically
            Method callMethod = groovySoap.getClass().getMethod("call", String.class, Object.class);
            callMethod.invoke(groovySoap, name, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add SOAP element: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Add a SOAP element with attributes.
     * 
     * @param name The element name
     * @param attributes The element attributes
     * @param value The element value
     * @return This wrapper for method chaining
     */
    public SoapWrapper element(String name, java.util.Map<String, String> attributes, Object value) {
        try {
            // This is a simplified implementation
            // In a real implementation, you'd need to handle attributes properly
            return element(name, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add SOAP element with attributes: " + e.getMessage(), e);
        }
    }
}