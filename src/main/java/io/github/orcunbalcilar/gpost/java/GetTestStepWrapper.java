package io.github.orcunbalcilar.gpost.java;

import groovy.lang.Closure;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Java wrapper for the Groovy GetTestStep class.
 */
public class GetTestStepWrapper {
    
    private final Object groovyStep;
    
    public GetTestStepWrapper(Object groovyStep) {
        this.groovyStep = groovyStep;
    }
    
    /**
     * Set the URL for the GET request.
     * 
     * @param url The URL to send the request to
     * @return This wrapper for method chaining
     */
    public GetTestStepWrapper url(String url) {
        try {
            Method urlMethod = groovyStep.getClass().getMethod("url", String.class);
            urlMethod.invoke(groovyStep, url);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set URL: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Set the name for this test step.
     * 
     * @param name The name of the test step
     * @return This wrapper for method chaining
     */
    public GetTestStepWrapper name(String name) {
        try {
            Field nameField = groovyStep.getClass().getField("name");
            nameField.set(groovyStep, name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set name: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Set the timeout for the request.
     * 
     * @param timeout The timeout in milliseconds
     * @return This wrapper for method chaining
     */
    public GetTestStepWrapper timeout(int timeout) {
        try {
            Method timeoutMethod = groovyStep.getClass().getMethod("timeout", int.class);
            timeoutMethod.invoke(groovyStep, timeout);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set timeout: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Configure the request for this test step.
     * 
     * @param config The request configuration
     * @return This wrapper for method chaining
     */
    public GetTestStepWrapper request(RequestConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call() {
                    RequestWrapper wrapper = new RequestWrapper(getDelegate());
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy request method
            Method requestMethod = groovyStep.getClass().getMethod("request", Closure.class);
            requestMethod.invoke(groovyStep, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure request: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Configure the assertions for this test step.
     * 
     * @param config The assertions configuration
     * @return This wrapper for method chaining
     */
    public GetTestStepWrapper assertions(AssertionsConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call() {
                    AssertionsWrapper wrapper = new AssertionsWrapper(getDelegate());
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy assertions method
            Method assertionsMethod = groovyStep.getClass().getMethod("assertions", Closure.class);
            assertionsMethod.invoke(groovyStep, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure assertions: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Interface for configuring a request.
     */
    @FunctionalInterface
    public interface RequestConfig {
        void configure(RequestWrapper request);
    }
    
    /**
     * Interface for configuring assertions.
     */
    @FunctionalInterface
    public interface AssertionsConfig {
        void configure(AssertionsWrapper assertions);
    }
}