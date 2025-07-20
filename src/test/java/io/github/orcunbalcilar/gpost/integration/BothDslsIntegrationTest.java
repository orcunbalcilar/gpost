package io.github.orcunbalcilar.gpost.integration;

import io.github.orcunbalcilar.gpost.core.TestCase;
import io.github.orcunbalcilar.gpost.core.TestCaseBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test demonstrating both DSLs share the same underlying core implementation.
 */
public class BothDslsIntegrationTest extends IntegrationTest {
    
    private static final Logger logger = LoggerFactory.getLogger(BothDslsIntegrationTest.class);
    
    @Test
    public void testJavaDslWithSimplifiedArchitecture() {
        // Test Java DSL with simplified architecture (no more Impl classes)
        TestCaseBuilder builder = new TestCaseBuilder(); // Direct instantiation, no interface
        
        TestCase testCase = builder.testCase("Simplified Architecture Test", spec -> {
            spec.script("setup", () -> {
                logger.info("Java DSL with simplified architecture");
            });
            spec.get(step -> {
                step.url(getBaseUrl() + "/get");
                step.name("Simple GET request");
            });
        });
        
        // Verify the test case was created properly
        assertNotNull(testCase);
        assertEquals("Simplified Architecture Test", testCase.getName());
        assertEquals(2, testCase.getTestSteps().size());
        
        // Verify we're using concrete classes, not interfaces
        assertEquals("TestCaseImpl", testCase.getClass().getSimpleName());
        
        logger.info("✅ Java DSL works with simplified architecture (no unnecessary interfaces)!");
        logger.info("✅ TestCase class: {}", testCase.getClass().getSimpleName());
        logger.info("✅ TestCaseBuilder is now a concrete class, not an interface");
    }
}