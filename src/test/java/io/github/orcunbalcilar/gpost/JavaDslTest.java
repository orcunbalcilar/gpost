package io.github.orcunbalcilar.gpost.java;

import io.github.orcunbalcilar.gpost.java.JavaTestCaseBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.Map;
import java.util.HashMap;

/**
 * Test class to verify the Java DSL functionality.
 */
public class JavaDslTest {
    
    @Test
    @Disabled("Integration test - requires network access")
    public void testJavaGetRequest() {
        JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
        
        // Test simple GET request
        Object testCase = builder.get(step -> {
            step.url("https://httpbin.org/get")
                .name("Simple GET Test")
                .timeout(30000)
                .request(request -> {
                    request.headers(headers -> {
                        headers.header("Accept", "application/json")
                               .header("User-Agent", "Java-DSL-Test");
                    });
                })
                .assertions(assertions -> {
                    assertions.statusCode(200);
                });
        });
        
        // The test case should be created successfully
        assert testCase != null;
        System.out.println("Java DSL GET test case created successfully");
    }
    
    @Test
    @Disabled("Integration test - requires network access")
    public void testJavaPostRequest() {
        JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
        
        // Test POST request with JSON body
        Object testCase = builder.testCase("Java POST Test", spec -> {
            spec.post(step -> {
                step.url("https://httpbin.org/post")
                    .name("POST with JSON")
                    .request(request -> {
                        request.headers(headers -> {
                            headers.contentType("application/json")
                                   .accept("application/json");
                        })
                        .body(body -> {
                            body.json("{\"message\": \"Hello from Java DSL\"}");
                        });
                    })
                    .assertions(assertions -> {
                        assertions.statusCode(200);
                    });
            });
        });
        
        // The test case should be created successfully
        assert testCase != null;
        System.out.println("Java DSL POST test case created successfully");
    }
    
    @Test
    @Disabled("Integration test - requires network access")
    public void testJavaSoapRequest() {
        JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
        
        // Test SOAP request similar to the Groovy example
        Object testCase = builder.testCase("Java SOAP Test", spec -> {
            spec.script("setUbiNum", () -> {
                System.out.println("Java script is running");
                // You would access context here to set properties
            });
            
            spec.post(step -> {
                step.url("https://www.dataaccess.com/webservicesserver/NumberConversion.wso")
                    .request(request -> {
                        request.body(body -> {
                            body.soap(soap -> {
                                soap.element("NumberToWords", null)
                                    .element("ubiNum", 903);
                            });
                        });
                    })
                    .assertions(assertions -> {
                        assertions.statusCode(200)
                                  .xml(xml -> {
                                      xml.equals("result", "nine hundred and three");
                                  });
                    });
            });
        });
        
        // The test case should be created successfully
        assert testCase != null;
        System.out.println("Java DSL SOAP test case created successfully");
    }
    
    @Test
    public void testJavaBuilderCreation() {
        // Test that we can create the builder without errors
        JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
        assert builder != null;
        System.out.println("Java DSL builder created successfully");
    }
}