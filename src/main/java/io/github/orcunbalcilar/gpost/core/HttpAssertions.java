package io.github.orcunbalcilar.gpost.core;

import java.util.function.Consumer;

/**
 * Core interface for HTTP response assertions.
 */
public interface HttpAssertions {
    
    /**
     * Assert on the status code.
     * 
     * @param expectedStatusCode The expected status code
     * @return This assertions for method chaining
     */
    HttpAssertions statusCode(int expectedStatusCode);
    
    /**
     * Assert that the response body contains the given text.
     * 
     * @param text The text to look for
     * @return This assertions for method chaining
     */
    HttpAssertions bodyContains(String text);
    
    /**
     * Assert that the response body equals the given text.
     * 
     * @param text The expected text
     * @return This assertions for method chaining
     */
    HttpAssertions bodyEquals(String text);
    
    /**
     * Assert on JSON response.
     * 
     * @param config The JSON assertions configuration
     * @return This assertions for method chaining
     */
    HttpAssertions json(Consumer<JsonAssertions> config);
    
    /**
     * Assert on XML response.
     * 
     * @param config The XML assertions configuration
     * @return This assertions for method chaining
     */
    HttpAssertions xml(Consumer<XmlAssertions> config);
    
    /**
     * Assert on response headers.
     * 
     * @param headerName The header name
     * @param expectedValue The expected header value
     * @return This assertions for method chaining
     */
    HttpAssertions header(String headerName, String expectedValue);
}