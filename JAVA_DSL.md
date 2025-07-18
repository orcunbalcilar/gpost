# Java DSL for gpost

The gpost library now supports Java DSL alongside the existing Groovy DSL. This allows Java developers to create HTTP test cases using a fluent API that wraps the underlying Groovy implementation.

## Features

- **Java-friendly fluent API**: Write HTTP test cases in Java using method chaining
- **Full feature parity**: All Groovy DSL features are available through the Java DSL
- **Seamless integration**: Java DSL works alongside existing Groovy DSL code
- **Same runtime behavior**: Both DSLs use the same underlying implementation

## Usage

### Basic GET Request

```java
import io.github.orcunbalcilar.gpost.java.JavaTestCaseBuilder;

public class MyTest {
    
    @Test
    public void testGetRequest() {
        JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
        
        Object testCase = builder.get(step -> {
            step.url("https://api.example.com/users")
                .name("Get Users")
                .timeout(30000)
                .request(request -> {
                    request.headers(headers -> {
                        headers.header("Accept", "application/json")
                               .header("Authorization", "Bearer token123");
                    });
                })
                .assertions(assertions -> {
                    assertions.statusCode(200);
                });
        });
        
        // Run the test case
        ((io.github.orcunbalcilar.gpost.testcase.TestCase) testCase).run();
    }
}
```

### POST Request with JSON Body

```java
@Test
public void testPostRequest() {
    JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
    
    Object testCase = builder.testCase("Create User Test", spec -> {
        spec.post(step -> {
            step.url("https://api.example.com/users")
                .name("Create User")
                .request(request -> {
                    request.headers(headers -> {
                        headers.contentType("application/json")
                               .accept("application/json");
                    })
                    .body(body -> {
                        body.json("{\\"name\\": \\"John Doe\\", \\"email\\": \\"john@example.com\\"}");
                    });
                })
                .assertions(assertions -> {
                    assertions.statusCode(201);
                });
        });
    });
    
    // Run the test case
    ((io.github.orcunbalcilar.gpost.testcase.TestCase) testCase).run();
}
```

### SOAP Request

```java
@Test
public void testSoapRequest() {
    JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
    
    Object testCase = builder.testCase("SOAP Service Test", spec -> {
        spec.script("setNumber", () -> {
            System.out.println("Setting up test data");
            // Access context to set properties if needed
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
                                  xml.equals("response", "nine hundred and three");
                              });
                });
        });
    });
    
    // Run the test case
    ((io.github.orcunbalcilar.gpost.testcase.TestCase) testCase).run();
}
```

### Complex Test Case with Multiple Steps

```java
@Test
public void testComplexWorkflow() {
    JavaTestCaseBuilder builder = new JavaTestCaseBuilder();
    
    Object testCase = builder.testCase("User Workflow Test", spec -> {
        // Step 1: Login
        spec.post(step -> {
            step.url("https://api.example.com/login")
                .name("Login")
                .request(request -> {
                    request.headers(headers -> {
                        headers.contentType("application/json");
                    })
                    .body(body -> {
                        body.json("{\\"username\\": \\"testuser\\", \\"password\\": \\"testpass\\"}");
                    });
                })
                .assertions(assertions -> {
                    assertions.statusCode(200);
                });
        });
        
        // Step 2: Get user profile
        spec.get(step -> {
            step.url("https://api.example.com/profile")
                .name("Get Profile")
                .request(request -> {
                    request.headers(headers -> {
                        headers.header("Authorization", "Bearer ${context.token}")
                               .accept("application/json");
                    });
                })
                .assertions(assertions -> {
                    assertions.statusCode(200)
                              .json(json -> {
                                  json.pathExists("$.user.id");
                              });
                });
        });
        
        // Step 3: Update profile
        spec.put(step -> {
            step.url("https://api.example.com/profile")
                .name("Update Profile")
                .request(request -> {
                    request.headers(headers -> {
                        headers.contentType("application/json")
                               .authorization("Bearer ${context.token}");
                    })
                    .body(body -> {
                        body.json("{\\"firstName\\": \\"Updated Name\\"}");
                    });
                })
                .assertions(assertions -> {
                    assertions.statusCode(200);
                });
        });
    });
    
    // Run the test case
    ((io.github.orcunbalcilar.gpost.testcase.TestCase) testCase).run();
}
```

## API Reference

### JavaTestCaseBuilder

The main entry point for creating test cases.

- `testCase(String name, TestCaseConfig config)`: Create a test case with multiple steps
- `get(GetRequestConfig config)`: Create a simple GET request test case
- `post(PostRequestConfig config)`: Create a simple POST request test case
- `put(PutRequestConfig config)`: Create a simple PUT request test case

### Test Step Configuration

Each test step can be configured with:

- `url(String url)`: Set the request URL
- `name(String name)`: Set the step name
- `timeout(int timeout)`: Set the request timeout in milliseconds
- `request(RequestConfig config)`: Configure the request
- `assertions(AssertionsConfig config)`: Configure assertions

### Request Configuration

For GET requests:
- `headers(HeadersConfig config)`: Configure request headers
- `params(Map<String, String> params)`: Set query parameters

For POST/PUT requests:
- `headers(HeadersConfig config)`: Configure request headers
- `body(BodyConfig config)`: Configure request body

### Headers Configuration

- `header(String name, String value)`: Add a custom header
- `contentType(String contentType)`: Set Content-Type header
- `accept(String accept)`: Set Accept header
- `authorization(String authorization)`: Set Authorization header

### Body Configuration

- `json(String jsonContent)`: Set JSON content
- `xml(String xmlContent)`: Set XML content
- `soap(SoapConfig config)`: Configure SOAP body

### Assertions Configuration

- `statusCode(int expectedCode)`: Assert response status code
- `bodyContains(String expectedText)`: Assert response body contains text
- `bodyEquals(String expectedBody)`: Assert response body equals text
- `xml(XmlAssertionsConfig config)`: Configure XML assertions
- `json(JsonAssertionsConfig config)`: Configure JSON assertions

## Comparison with Groovy DSL

### Groovy DSL
```groovy
testCase "API Test", {
    get {
        url "https://api.example.com/users"
        request {
            headers {
                "Accept" "application/json"
                "Authorization" "Bearer token"
            }
        }
        assertions {
            statusCode 200
        }
    }
}
```

### Java DSL
```java
builder.testCase("API Test", spec -> {
    spec.get(step -> {
        step.url("https://api.example.com/users")
            .request(request -> {
                request.headers(headers -> {
                    headers.header("Accept", "application/json")
                           .header("Authorization", "Bearer token");
                });
            })
            .assertions(assertions -> {
                assertions.statusCode(200);
            });
    });
});
```

Both DSLs provide the same functionality with their respective language idioms.

## Integration with Existing Code

The Java DSL can be used alongside existing Groovy DSL code without any conflicts. Both produce the same `TestCase` objects and use the same runtime execution engine.

## Type Safety

The Java DSL provides compile-time type safety while maintaining the flexibility of the underlying Groovy implementation. All configuration is validated at compile time, reducing runtime errors.