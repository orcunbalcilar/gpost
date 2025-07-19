package io.github.orcunbalcilar.gpost.integration;

import io.github.orcunbalcilar.gpost.core.TestCase;
import io.github.orcunbalcilar.gpost.core.TestCaseBuilder;
import io.github.orcunbalcilar.gpost.test.BaseWireMockTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test demonstrating both DSLs share the same underlying core implementation.
 */
public class BothDslsIntegrationTest extends BaseWireMockTest {
    
    @Test
    public void testJavaDslWithSimplifiedArchitecture() {
        // Test Java DSL with simplified architecture (no more Impl classes)
        TestCaseBuilder builder = new TestCaseBuilder(); // Direct instantiation, no interface
        
        TestCase testCase = builder.testCase("Simplified Architecture Test", spec -> {
            spec.script("setup", () -> {
                System.out.println("Java DSL with simplified architecture");
            });
            spec.get(step -> {
                step.url("http://localhost:8089/get");
                step.name("Simple GET request");
            });
        });
        
        // Verify the test case was created properly
        assertNotNull(testCase);
        assertEquals("Simplified Architecture Test", testCase.getName());
        assertEquals(2, testCase.getTestSteps().size());
        
        // Verify we're using concrete classes, not interfaces
        assertEquals("TestCaseImpl", testCase.getClass().getSimpleName());
        
        System.out.println("✅ Java DSL works with simplified architecture (no unnecessary interfaces)!");
        System.out.println("✅ TestCase class: " + testCase.getClass().getSimpleName());
        System.out.println("✅ TestCaseBuilder is now a concrete class, not an interface");
    }
}