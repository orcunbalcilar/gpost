package io.github.orcunbalcilar.gpost.integration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test to verify that WireMock stub mappings are loaded from JSON files.
 */
class WireMockJsonStubTest extends IntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(WireMockJsonStubTest.class);

    @Test
    void shouldLoadStubMappingsFromJsonFiles() throws Exception {
        // Given - WireMock server is running with JSON stub mappings loaded from files

        // When - Make a request to an endpoint defined in our JSON stub files
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/get"))
                .header("Accept", "application/json")
                .header("User-Agent", "JSON-Stub-Test")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Then - Verify that the response matches our JSON stub definition
        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.headers().firstValue("Content-Type").orElse(""));
        
        String responseBody = response.body();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("args"));
        assertTrue(responseBody.contains("headers"));
        assertTrue(responseBody.contains("url"));
        
        logger.info("Successfully verified JSON stub mapping for GET /get endpoint");
        logger.debug("Response: {}", responseBody);
    }

    @Test
    void shouldLoadBasicAuthStubFromJsonFile() throws Exception {
        // Given - WireMock server with basic auth stub loaded from JSON

        // When - Make authenticated request
        HttpClient client = HttpClient.newHttpClient();
        String credentials = java.util.Base64.getEncoder()
                .encodeToString("user:pass".getBytes());
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/basic-auth/user/pass"))
                .header("Authorization", "Basic " + credentials)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Then - Verify successful authentication as defined in JSON stub
        assertEquals(200, response.statusCode());
        String responseBody = response.body();
        assertTrue(responseBody.contains("\"authenticated\": true"));
        assertTrue(responseBody.contains("\"user\": \"user\""));
        
        logger.info("Successfully verified JSON stub mapping for basic auth endpoint");
        logger.debug("Response: {}", responseBody);
    }

    @Test
    void shouldLoadErrorStubFromJsonFile() throws Exception {
        // Given - WireMock server with error stub loaded from JSON

        // When - Make request to error endpoint
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getBaseUrl() + "/status/404"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Then - Verify error response as defined in JSON stub
        assertEquals(404, response.statusCode());
        String responseBody = response.body();
        assertTrue(responseBody.contains("\"error\": \"Not Found\""));
        assertTrue(responseBody.contains("\"message\": \"The requested resource was not found\""));
        
        logger.info("Successfully verified JSON stub mapping for 404 error endpoint");
        logger.debug("Response: {}", responseBody);
    }
}
