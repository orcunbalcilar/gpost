package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.TestCase;
import io.github.orcunbalcilar.gpost.core.TestCaseSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCaseSpecImplTest {
    
    private TestCase testCase;
    private TestCaseSpec spec;
    
    @BeforeEach
    void setUp() {
        testCase = new TestCaseImpl();
        spec = new TestCaseSpecImpl(testCase);
    }
    
    @Test
    void testGetTestCase() {
        assertSame(testCase, spec.getTestCase());
    }
    
    @Test
    void testGetContext() {
        assertNotNull(spec.getContext());
    }
    
    @Test
    void testGetRequest() {
        TestCaseSpec result = spec.get(getStep -> {
            getStep.url("http://example.com");
            getStep.name("Test GET request");
        });
        
        assertSame(spec, result);
        // Verify that a test step was added
        assertEquals(1, testCase.getTestSteps().size());
    }
    
    @Test
    void testPostRequest() {
        TestCaseSpec result = spec.post(postStep -> {
            postStep.url("http://example.com/api");
            postStep.name("Test POST request");
        });
        
        assertSame(spec, result);
        assertEquals(1, testCase.getTestSteps().size());
    }
    
    @Test
    void testPutRequest() {
        TestCaseSpec result = spec.put(putStep -> {
            putStep.url("http://example.com/api/1");
            putStep.name("Test PUT request");
        });
        
        assertSame(spec, result);
        assertEquals(1, testCase.getTestSteps().size());
    }
    
    @Test
    void testScriptStep() {
        boolean[] scriptExecuted = {false};
        
        TestCaseSpec result = spec.script("test script", () -> {
            scriptExecuted[0] = true;
        });
        
        assertSame(spec, result);
        assertEquals(1, testCase.getTestSteps().size());
        
        // Execute the script step to verify it works
        testCase.getTestSteps().get(0).run();
        assertTrue(scriptExecuted[0]);
    }
    
    @Test
    void testMultipleSteps() {
        spec.get(getStep -> getStep.url("http://example.com/1"))
            .post(postStep -> postStep.url("http://example.com/2"))
            .put(putStep -> putStep.url("http://example.com/3"))
            .script("test", () -> {});
        
        assertEquals(4, testCase.getTestSteps().size());
    }
    
    @Test
    void testBasicAuthConfiguration() {
        spec.basicAuth(basicAuth -> {
            basicAuth.username("testuser");
            basicAuth.password("testpass");
        });
        
        assertNotNull(spec.getAuth());
        assertEquals("basic", spec.getAuth().getType());
    }
    
    @Test
    void testInitialAuthIsNull() {
        assertNull(spec.getAuth());
    }
    
    @Test
    void testComplexTestCase() {
        spec.script("setup", () -> {
            spec.getContext().setProperty("baseUrl", "http://example.com");
        })
        .get(getStep -> {
            getStep.url("http://example.com/api/users");
            getStep.name("Get users");
            getStep.assertions(assertions -> {
                assertions.statusCode(200);
                assertions.bodyContains("users");
            });
        })
        .post(postStep -> {
            postStep.url("http://example.com/api/users");
            postStep.name("Create user");
            postStep.requestWithBody(request -> {
                request.jsonBody("{\"name\": \"John\"}");
            });
            postStep.assertions(assertions -> {
                assertions.statusCode(201);
            });
        });
        
        assertEquals(3, testCase.getTestSteps().size());
    }
    
    @Test
    void testStepConfiguration() {
        spec.get(getStep -> {
            getStep.url("http://example.com")
                   .name("Complex GET request")
                   .request(request -> {
                       request.header("Accept", "application/json")
                              .header("Authorization", "Bearer token");
                   })
                   .assertions(assertions -> {
                       assertions.statusCode(200)
                                .bodyContains("success")
                                .header("Content-Type", "application/json");
                   });
        });
        
        assertEquals(1, testCase.getTestSteps().size());
    }
}