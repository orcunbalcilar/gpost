package io.github.orcunbalcilar.gpost.integration;

import io.github.orcunbalcilar.gpost.core.TestCase;
import io.github.orcunbalcilar.gpost.core.TestCaseBuilder;
import io.github.orcunbalcilar.gpost.core.impl.TestCaseBuilderImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests demonstrating both Java and Groovy DSLs working together.
 */
class DslIntegrationTest {
    
    @Test
    void testJavaDslBasicUsage() {
        TestCaseBuilder builder = new TestCaseBuilderImpl();
        
        TestCase testCase = builder.testCase("Java DSL Test", spec -> {
            spec.script("setup", () -> {
                spec.getContext().setProperty("baseUrl", "http://localhost:8089");
            });
            
            spec.get(getStep -> {
                getStep.url("http://localhost:8089/api/users")
                       .name("Get users")
                       .request(request -> {
                           request.header("Accept", "application/json")
                                  .basicAuth("user", "pass");
                       })
                       .assertions(assertions -> {
                           assertions.statusCode(200)
                                    .bodyContains("users");
                       });
            });
            
            spec.post(postStep -> {
                postStep.url("http://localhost:8089/api/users")
                        .name("Create user")
                        .requestWithBody(request -> {
                            request.jsonBody("{\"name\": \"John Doe\", \"email\": \"john@example.com\"}")
                                   .header("Content-Type", "application/json");
                        })
                        .assertions(assertions -> {
                            assertions.statusCode(201);
                        });
            });
        });
        
        // Assertions
        assertNotNull(testCase);
        assertEquals("Java DSL Test", testCase.getName());
        assertEquals(3, testCase.getTestSteps().size());
    }
    
    @Test
    void testJavaDslWithAuthentication() {
        TestCaseBuilder builder = new TestCaseBuilderImpl();
        
        TestCase testCase = builder.testCase("Auth Test", spec -> {
            spec.basicAuth(basicAuth -> {
                basicAuth.username("testuser");
                basicAuth.password("testpass");
            });
            
            spec.get(getStep -> {
                getStep.url("http://localhost:8089/secure")
                       .name("Secure endpoint")
                       .request(request -> {
                           request.basicAuth(auth -> {
                               auth.username("user");
                               auth.password("pass");
                           });
                       });
            });
        });
        
        assertNotNull(testCase);
        assertEquals("Auth Test", testCase.getName());
        assertNotNull(testCase.getTestSteps().get(0));
    }
    
    @Test
    void testJavaDslWithRequestBody() {
        TestCaseBuilder builder = new TestCaseBuilderImpl();
        
        TestCase testCase = builder.testCase("Request Body Test", spec -> {
            spec.post(postStep -> {
                postStep.url("http://localhost:8089/api/data")
                        .name("POST with body")
                        .requestWithBody(request -> {
                            request.requestBody(bodyBuilder -> {
                                bodyBuilder.json("{\"data\": \"test\"}");
                            });
                        });
            });
            
            spec.put(putStep -> {
                putStep.url("http://localhost:8089/api/data/1")
                       .name("PUT with XML body")
                       .requestWithBody(request -> {
                           request.xmlBody("<data>test</data>");
                       });
            });
        });
        
        assertNotNull(testCase);
        assertEquals(2, testCase.getTestSteps().size());
    }
    
    @Test
    void testJavaDslWithResponseBodyAssertions() {
        TestCaseBuilder builder = new TestCaseBuilderImpl();
        
        TestCase testCase = builder.testCase("Response Body Test", spec -> {
            spec.get(getStep -> {
                getStep.url("http://localhost:8089/api/json")
                       .name("JSON response test")
                       .assertions(assertions -> {
                           assertions.statusCode(200)
                                    .bodyContains("success");
                           // Note: body assertions with actual response data would require 
                           // a real HTTP response, which is not available in unit tests
                       });
            });
        });
        
        assertNotNull(testCase);
        assertEquals(1, testCase.getTestSteps().size());
    }
    
    @Test
    void testJavaDslContextUsage() {
        TestCaseBuilder builder = new TestCaseBuilderImpl();
        
        TestCase testCase = builder.testCase("Context Test", spec -> {
            spec.script("setup", () -> {
                spec.getContext().setProperty("username", "testuser");
                spec.getContext().setProperty("password", "testpass");
                spec.getContext().setProperty("baseUrl", "http://localhost:8089");
            });
            
            spec.get(getStep -> {
                getStep.url("http://localhost:8089/api/profile")
                       .name("Get profile")
                       .request(request -> {
                           String username = spec.getContext().getProperty("username", String.class);
                           String password = spec.getContext().getProperty("password", String.class);
                           request.basicAuth(username, password);
                       });
            });
        });
        
        assertNotNull(testCase);
        assertEquals(2, testCase.getTestSteps().size());
    }
    
    @Test
    void testJavaDslComplexScenario() {
        TestCaseBuilder builder = new TestCaseBuilderImpl();
        
        TestCase testCase = builder.testCase("Complex API Test", spec -> {
            // Setup
            spec.script("setup", () -> {
                spec.getContext().setProperty("baseUrl", "http://localhost:8089");
                spec.getContext().setProperty("authToken", "bearer-token-123");
            });
            
            // Login
            spec.post(postStep -> {
                postStep.url("http://localhost:8089/auth/login")
                        .name("Login")
                        .requestWithBody(request -> {
                            request.jsonBody("{\"username\": \"admin\", \"password\": \"secret\"}")
                                   .header("Content-Type", "application/json");
                        })
                        .assertions(assertions -> {
                            assertions.statusCode(200)
                                     .header("Content-Type", "application/json")
                                     .bodyContains("token");
                        });
            });
            
            // Get user profile
            spec.get(getStep -> {
                getStep.url("http://localhost:8089/api/profile")
                       .name("Get profile")
                       .request(request -> {
                           request.header("Authorization", "Bearer " + spec.getContext().getProperty("authToken"));
                       })
                       .assertions(assertions -> {
                           assertions.statusCode(200)
                                    .bodyContains("profile");
                       });
            });
            
            // Update profile
            spec.put(putStep -> {
                putStep.url("http://localhost:8089/api/profile")
                       .name("Update profile")
                       .requestWithBody(request -> {
                           request.jsonBody("{\"name\": \"Updated Name\", \"email\": \"updated@example.com\"}")
                                  .header("Authorization", "Bearer " + spec.getContext().getProperty("authToken"))
                                  .header("Content-Type", "application/json");
                       })
                       .assertions(assertions -> {
                           assertions.statusCode(200);
                       });
            });
            
            // Cleanup
            spec.script("cleanup", () -> {
                spec.getContext().clearProperties();
            });
        });
        
        assertNotNull(testCase);
        assertEquals("Complex API Test", testCase.getName());
        assertEquals(5, testCase.getTestSteps().size());
    }
}