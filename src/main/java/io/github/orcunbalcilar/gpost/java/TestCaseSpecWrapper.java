package io.github.orcunbalcilar.gpost.java;

import groovy.lang.Closure;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Java wrapper for the Groovy TestCaseSpec class.
 */
public class TestCaseSpecWrapper {
    
    private final Object groovySpec;
    
    public TestCaseSpecWrapper(Object groovySpec) {
        this.groovySpec = groovySpec;
    }
    
    /**
     * Add a GET request test step.
     * 
     * @param config The GET request configuration
     * @return This wrapper for method chaining
     */
    public TestCaseSpecWrapper get(JavaTestCaseBuilder.GetRequestConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call() {
                    GetTestStepWrapper wrapper = new GetTestStepWrapper(getDelegate());
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy get method
            Method getMethod = groovySpec.getClass().getMethod("get", Closure.class);
            getMethod.invoke(groovySpec, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure GET request: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Add a POST request test step.
     * 
     * @param config The POST request configuration
     * @return This wrapper for method chaining
     */
    public TestCaseSpecWrapper post(JavaTestCaseBuilder.PostRequestConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call() {
                    PostTestStepWrapper wrapper = new PostTestStepWrapper(getDelegate());
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy post method
            Method postMethod = groovySpec.getClass().getMethod("post", Closure.class);
            postMethod.invoke(groovySpec, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure POST request: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Add a PUT request test step.
     * 
     * @param config The PUT request configuration
     * @return This wrapper for method chaining
     */
    public TestCaseSpecWrapper put(JavaTestCaseBuilder.PutRequestConfig config) {
        try {
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call() {
                    PutTestStepWrapper wrapper = new PutTestStepWrapper(getDelegate());
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy put method
            Method putMethod = groovySpec.getClass().getMethod("put", Closure.class);
            putMethod.invoke(groovySpec, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure PUT request: " + e.getMessage(), e);
        }
        return this;
    }
    
    /**
     * Add a script test step.
     * 
     * @param name The script name
     * @param script The script to execute
     * @return This wrapper for method chaining
     */
    public TestCaseSpecWrapper script(String name, Runnable script) {
        try {
            // Create a Groovy closure that wraps the Java script
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call() {
                    script.run();
                    return null;
                }
            };
            
            // Call the Groovy script method
            Method scriptMethod = groovySpec.getClass().getMethod("script", String.class, Closure.class);
            scriptMethod.invoke(groovySpec, name, groovyClosure);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure script: " + e.getMessage(), e);
        }
        return this;
    }
}