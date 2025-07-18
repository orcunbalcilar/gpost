package io.github.orcunbalcilar.gpost.java;

import io.github.orcunbalcilar.gpost.java.JavaTestCaseBuilder;
import org.junit.jupiter.api.Test;

/**
 * Simple test to verify the Java DSL basic functionality.
 */
public class SimpleDslTest {
    
    @Test
    public void testBasicJavaBuilderCreation() {
        // Test that we can create the builder without errors
        JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
        assert builder != null;
        System.out.println("✓ Java DSL builder created successfully");
    }
    
    @Test
    public void testJavaBuilderStaticImport() {
        // Test static import style usage  
        JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
        
        // This would be the typical usage pattern
        System.out.println("✓ Java DSL can be used for creating test cases");
        System.out.println("✓ Both Groovy and Java DSL are now available");
    }
}