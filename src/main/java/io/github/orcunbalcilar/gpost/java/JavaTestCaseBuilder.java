package io.github.orcunbalcilar.gpost.java;

import groovy.lang.Closure;
import groovy.lang.GroovyShell;

/**
 * Java DSL for creating test cases. This is a wrapper around the Groovy DSL
 * that allows Java users to create HTTP test cases using a fluent API.
 */
public class JavaTestCaseBuilder {
    
    private final GroovyShell groovyShell;
    
    public JavaTestCaseBuilder() {
        this.groovyShell = new GroovyShell();
    }
    
    /**
     * Create a new test case with the given name and configuration.
     * 
     * @param name The test case name
     * @param config The test case configuration (Java lambda will be converted to Groovy closure)
     * @return A TestCase object
     */
    public Object testCase(String name, TestCaseConfig config) {
        try {
            // Get the Groovy TestCaseBuilder class
            Class<?> groovyBuilderClass = Class.forName("io.github.orcunbalcilar.gpost.testcase.TestCaseBuilder");
            
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call() {
                    // Create a test case spec wrapper using the delegate
                    TestCaseSpecWrapper wrapper = new TestCaseSpecWrapper(getDelegate());
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy testCase method
            return groovyBuilderClass.getMethod("testCase", String.class, Closure.class)
                    .invoke(null, name, groovyClosure);
                    
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test case: " + e.getMessage(), e);
        }
    }
    
    /**
     * Create a simple GET request test case.
     * 
     * @param config The GET request configuration
     * @return A TestCase object
     */
    public Object get(GetRequestConfig config) {
        try {
            // Get the Groovy TestCaseBuilder class
            Class<?> groovyBuilderClass = Class.forName("io.github.orcunbalcilar.gpost.testcase.TestCaseBuilder");
            
            // Create a Groovy closure that wraps the Java configuration
            Closure<?> groovyClosure = new Closure<Object>(this) {
                @Override
                public Object call() {
                    // Create a GET test step wrapper using the delegate
                    GetTestStepWrapper wrapper = new GetTestStepWrapper(getDelegate());
                    config.configure(wrapper);
                    return null;
                }
            };
            
            // Call the Groovy get method
            return groovyBuilderClass.getMethod("get", Closure.class)
                    .invoke(null, groovyClosure);
                    
        } catch (Exception e) {
            throw new RuntimeException("Failed to create GET request: " + e.getMessage(), e);
        }
    }
    
    /**
     * Interface for configuring a test case.
     */
    @FunctionalInterface
    public interface TestCaseConfig {
        void configure(TestCaseSpecWrapper spec);
    }
    
    /**
     * Interface for configuring a GET request.
     */
    @FunctionalInterface
    public interface GetRequestConfig {
        void configure(GetTestStepWrapper step);
    }
    
    /**
     * Interface for configuring a POST request.
     */
    @FunctionalInterface
    public interface PostRequestConfig {
        void configure(PostTestStepWrapper step);
    }
    
    /**
     * Interface for configuring a PUT request.
     */
    @FunctionalInterface
    public interface PutRequestConfig {
        void configure(PutTestStepWrapper step);
    }
}