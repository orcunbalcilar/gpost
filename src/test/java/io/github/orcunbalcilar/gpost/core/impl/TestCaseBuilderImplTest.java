package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.TestCase;
import io.github.orcunbalcilar.gpost.core.TestCaseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCaseBuilderImplTest {
    
    private TestCaseBuilder builder;
    
    @BeforeEach
    void setUp() {
        builder = new TestCaseBuilder();
    }
    
    @Test
    void testSimpleGetRequest() {
        TestCase testCase = builder.get(getStep -> {
            getStep.url("http://example.com");
            getStep.name("Simple GET");
        });
        
        assertNotNull(testCase);
        assertNotNull(testCase.getName());
        assertEquals(1, testCase.getTestSteps().size());
    }
    
    @Test
    void testTestCaseWithName() {
        TestCase testCase = builder.testCase("My Test Case", spec -> {
            spec.get(getStep -> {
                getStep.url("http://example.com");
                getStep.name("GET request");
            });
        });
        
        assertNotNull(testCase);
        assertEquals("My Test Case", testCase.getName());
        assertEquals(1, testCase.getTestSteps().size());
    }
    
    @Test
    void testComplexTestCase() {
        TestCase testCase = builder.testCase("Complex Test", spec -> {
            spec.script("setup", () -> {
                spec.getContext().setProperty("baseUrl", "http://example.com");
            });
            
            spec.get(getStep -> {
                getStep.url("http://example.com/api/users");
                getStep.name("Get users");
                getStep.assertions(assertions -> {
                    assertions.statusCode(200);
                });
            });
            
            spec.post(postStep -> {
                postStep.url("http://example.com/api/users");
                postStep.name("Create user");
                postStep.requestWithBody(request -> {
                    request.jsonBody("{\"name\": \"John\"}");
                });
            });
        });
        
        assertNotNull(testCase);
        assertEquals("Complex Test", testCase.getName());
        assertEquals(3, testCase.getTestSteps().size());
    }
    
    @Test
    void testMultipleTestCases() {
        TestCase testCase1 = builder.testCase("Test 1", spec -> {
            spec.get(getStep -> getStep.url("http://example.com/1"));
        });
        
        TestCase testCase2 = builder.testCase("Test 2", spec -> {
            spec.post(postStep -> postStep.url("http://example.com/2"));
        });
        
        assertNotSame(testCase1, testCase2);
        assertEquals("Test 1", testCase1.getName());
        assertEquals("Test 2", testCase2.getName());
    }
    
    @Test
    void testEmptyTestCase() {
        TestCase testCase = builder.testCase("Empty Test", spec -> {
            // No steps added
        });
        
        assertNotNull(testCase);
        assertEquals("Empty Test", testCase.getName());
        assertEquals(0, testCase.getTestSteps().size());
    }
}