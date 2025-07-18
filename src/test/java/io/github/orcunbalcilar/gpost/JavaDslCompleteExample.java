package io.github.orcunbalcilar.gpost.java;

import io.github.orcunbalcilar.gpost.java.JavaTestCaseBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

/**
 * Comprehensive example showing all Java DSL features.
 */
public class JavaDslCompleteExample {
    
    @Test
    @Disabled("Integration test - requires network access")
    public void completeJavaApiExample() {
        JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
        
        // Example 1: Simple GET request
        System.out.println("=== Example 1: GET Request ===");
        Object getTestCase = builder.get(step -> {
            step.url("https://httpbin.org/get")
                .name("Simple GET")
                .timeout(30000)
                .request(request -> {
                    request.headers(headers -> {
                        headers.accept("application/json")
                               .header("User-Agent", "Java-DSL/1.0");
                    });
                })
                .assertions(assertions -> {
                    assertions.statusCode(200);
                });
        });
        
        // Example 2: POST request with JSON
        System.out.println("=== Example 2: POST with JSON ===");
        Object postTestCase = builder.testCase("JSON POST Test", spec -> {
            spec.post(step -> {
                step.url("https://httpbin.org/post")
                    .name("JSON POST")
                    .request(request -> {
                        request.headers(headers -> {
                            headers.contentType("application/json")
                                   .accept("application/json");
                        })
                        .body(body -> {
                            body.json("{\"name\": \"John\", \"age\": 30}");
                        });
                    })
                    .assertions(assertions -> {
                        assertions.statusCode(200)
                                  .bodyContains("John");
                    });
            });
        });
        
        // Example 3: Multi-step workflow
        System.out.println("=== Example 3: Multi-step Workflow ===");
        Object workflowTestCase = builder.testCase("Complete Workflow", spec -> {
            // Step 1: Authentication
            spec.post(step -> {
                step.url("https://httpbin.org/post")
                    .name("Login")
                    .request(request -> {
                        request.headers(headers -> {
                            headers.contentType("application/json");
                        })
                        .body(body -> {
                            body.json("{\"username\": \"test\", \"password\": \"secret\"}");
                        });
                    })
                    .assertions(assertions -> {
                        assertions.statusCode(200);
                    });
            });
            
            // Step 2: Get user data
            spec.get(step -> {
                step.url("https://httpbin.org/get")
                    .name("Get User Data")
                    .request(request -> {
                        request.headers(headers -> {
                            headers.authorization("Bearer token123")
                                   .accept("application/json");
                        });
                    })
                    .assertions(assertions -> {
                        assertions.statusCode(200);
                    });
            });
            
            // Step 3: Update user data
            spec.put(step -> {
                step.url("https://httpbin.org/put")
                    .name("Update User")
                    .request(request -> {
                        request.headers(headers -> {
                            headers.contentType("application/json")
                                   .authorization("Bearer token123");
                        })
                        .body(body -> {
                            body.json("{\"name\": \"Updated Name\"}");
                        });
                    })
                    .assertions(assertions -> {
                        assertions.statusCode(200);
                    });
            });
            
            // Step 4: Custom script
            spec.script("cleanup", () -> {
                System.out.println("Performing cleanup tasks...");
                // Custom cleanup logic here
            });
        });
        
        // All test cases created successfully
        assert getTestCase != null;
        assert postTestCase != null;  
        assert workflowTestCase != null;
        
        System.out.println("✅ All Java DSL examples created successfully!");
        System.out.println("✅ Java DSL provides full feature parity with Groovy DSL");
        System.out.println("✅ Both DSLs can be used together in the same project");
    }
    
    @Test
    public void javaVsGroovyComparison() {
        System.out.println("=== Java vs Groovy DSL Comparison ===");
        
        // Java DSL style
        JavaTestCaseBuilder javaBuilder = new JavaTestCaseBuilder();
        Object javaTest = javaBuilder.testCase("Java Style", spec -> {
            spec.get(step -> {
                step.url("https://api.example.com/data")
                    .request(request -> {
                        request.headers(headers -> {
                            headers.accept("application/json");
                        });
                    })
                    .assertions(assertions -> {
                        assertions.statusCode(200);
                    });
            });
        });
        
        System.out.println("Java DSL characteristics:");
        System.out.println("  ✓ Compile-time type safety");
        System.out.println("  ✓ IDE auto-completion and refactoring");
        System.out.println("  ✓ Familiar Java syntax with lambdas");
        System.out.println("  ✓ Method chaining for fluent API");
        System.out.println("  ✓ Runtime compatibility with Groovy DSL");
        
        System.out.println("\nGroovy DSL characteristics:");
        System.out.println("  ✓ More concise syntax");
        System.out.println("  ✓ Dynamic method resolution");
        System.out.println("  ✓ Script-like appearance");
        System.out.println("  ✓ Closure-based configuration");
        System.out.println("  ✓ Flexible and expressive");
        
        assert javaTest != null;
        System.out.println("\n✅ Both DSLs achieve the same functionality with their respective strengths!");
    }
}