package io.github.orcunbalcilar.gpost.java;

import groovy.lang.Closure;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Java wrapper for the Groovy PutTestStep class.
 */
public class PutTestStepWrapper {
    
    private final Object groovyStep;
    
    public PutTestStepWrapper(Object groovyStep) {
        this.groovyStep = groovyStep;
    }
    
    /**
     * Set the URL for the PUT request.
     * 
     * @param url The URL to send the request to
     * @return This wrapper for method chaining
     */
    public PutTestStepWrapper url(String url) {
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
    public PutTestStepWrapper name(String name) {
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
    public PutTestStepWrapper timeout(int timeout) {
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
    public PutTestStepWrapper request(PostTestStepWrapper.RequestWithBodyConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    RequestWithBodyWrapper wrapper = new RequestWithBodyWrapper(args[0]);
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
    public PutTestStepWrapper assertions(GetTestStepWrapper.AssertionsConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call(Object... args) {
                    AssertionsWrapper wrapper = new AssertionsWrapper(args[0]);
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
}