package io.github.orcunbalcilar.gpost.java;

import groovy.lang.Closure;
import java.lang.reflect.Method;

/**
 * Java wrapper for the Groovy body configuration.
 */
public class BodyWrapper {
    
    private final Object groovyBody;
    
    public BodyWrapper(Object groovyBody) {
        this.groovyBody = groovyBody;
    }
    
    /**
     * Set JSON content for the request body.
     * 
     * @param jsonContent The JSON content as a string
     * @return This wrapper for method chaining
     */
    public BodyWrapper json(String jsonContent) {
        try {
            // Create a Groovy closure that sets JSON content
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    // This would be the JSON content
                    return jsonContent;
                }
            };
            
            // Call the Groovy json method
            Method jsonMethod = groovyBody.getClass().getMethod("json", Closure.class);
            jsonMethod.invoke(groovyBody, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to set JSON content: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Set XML content for the request body.
     * 
     * @param xmlContent The XML content as a string
     * @return This wrapper for method chaining
     */
    public BodyWrapper xml(String xmlContent) {
        try {
            // Create a Groovy closure that sets XML content
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    // This would be the XML content
                    return xmlContent;
                }
            };
            
            // Call the Groovy xml method
            Method xmlMethod = groovyBody.getClass().getMethod("xml", Closure.class);
            xmlMethod.invoke(groovyBody, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to set XML content: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Set SOAP content for the request body.
     * 
     * @param config The SOAP configuration
     * @return This wrapper for method chaining
     */
    public BodyWrapper soap(SoapConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    SoapWrapper wrapper = new SoapWrapper(args[0]);
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy soap method
            Method soapMethod = groovyBody.getClass().getMethod("soap", Closure.class);
            soapMethod.invoke(groovyBody, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure SOAP: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Interface for configuring SOAP body.
     */
    @FunctionalInterface
    public interface SoapConfig {
        void configure(SoapWrapper soap);
    }
}