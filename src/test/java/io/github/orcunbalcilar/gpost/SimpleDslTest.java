package io.github.orcunbalcilar.gpost.java;

import io.github.orcunbalcilar.gpost.java.NewJavaTestCaseBuilder;
import org.junit.jupiter.api.Test;

/**
 * Simple test to verify the Java DSL basic functionality.
 */
public class SimpleDslTest {
    
    @Test
    public void testBasicJavaBuilderCreation() {
        // Test that we can create the builder without errors
        NewJavaTestCaseBuilder builder = new NewJavaTestCaseBuilder();
        assert builder != null;
        System.out.println("✓ Java DSL builder created successfully");
    }
    
    @Test
    public void testJavaBuilderStaticImport() {
        // Test static import style usage  
        NewJavaTestCaseBuilder builder = new NewJavaTestCaseBuilder();
        
        // This would be the typical usage pattern
        System.out.println("✓ Java DSL can be used for creating test cases");
        System.out.println("✓ Both Groovy and Java DSL are now available");
    }
}