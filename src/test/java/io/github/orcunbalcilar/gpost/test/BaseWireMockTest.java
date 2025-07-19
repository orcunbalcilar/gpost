package io.github.orcunbalcilar.gpost.test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Base test class that provides WireMock virtual services for testing.
 * This eliminates the need to rely on external APIs which can break the build process.
 */
public abstract class BaseWireMockTest {
    
    protected WireMockServer wireMockServer;
    protected String baseUrl;
    protected int serverPort;
    
    @BeforeEach
    void setUp() {
        // Use random port to avoid conflicts in parallel test execution
        serverPort = findAvailablePort();
        wireMockServer = new WireMockServer(serverPort);
        wireMockServer.start();
        baseUrl = "http://localhost:" + serverPort;
        
        // Configure WireMock to use our server
        WireMock.configureFor("localhost", serverPort);
        
        // Set up common mock endpoints
        setupCommonMocks();
    }
    
    @AfterEach
    void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
    
    /**
     * Find an available port for WireMock server.
     * @return An available port number
     */
    private int findAvailablePort() {
        // Use a range starting from 8090 to avoid conflicts with default 8089
        Random random = new Random();
        return 8090 + random.nextInt(1000); // Random port between 8090-9089
    }
    
    /**
     * Sets up common mock endpoints that replace external services.
     */
    protected void setupCommonMocks() {
        // Mock httpbin.org/get endpoint
        stubFor(get(urlEqualTo("/get"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"args\": {},\n" +
                                "  \"headers\": {\n" +
                                "    \"Accept\": \"application/json\",\n" +
                                "    \"User-Agent\": \"Java-DSL-Test\"\n" +
                                "  },\n" +
                                "  \"origin\": \"127.0.0.1\",\n" +
                                "  \"url\": \"" + baseUrl + "/get\"\n" +
                                "}")));
        
        // Mock httpbin.org/post endpoint
        stubFor(post(urlEqualTo("/post"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"args\": {},\n" +
                                "  \"data\": \"{\\\"message\\\": \\\"Hello from Java DSL\\\"}\",\n" +
                                "  \"files\": {},\n" +
                                "  \"form\": {},\n" +
                                "  \"headers\": {\n" +
                                "    \"Accept\": \"application/json\",\n" +
                                "    \"Content-Type\": \"application/json\"\n" +
                                "  },\n" +
                                "  \"json\": {\n" +
                                "    \"message\": \"Hello from Java DSL\"\n" +
                                "  },\n" +
                                "  \"origin\": \"127.0.0.1\",\n" +
                                "  \"url\": \"" + baseUrl + "/post\"\n" +
                                "}")));
        
        // Mock httpbin.org/put endpoint
        stubFor(put(urlEqualTo("/put"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"args\": {},\n" +
                                "  \"data\": \"{\\\"status\\\": \\\"updated\\\"}\",\n" +
                                "  \"files\": {},\n" +
                                "  \"form\": {},\n" +
                                "  \"headers\": {\n" +
                                "    \"Accept\": \"application/json\",\n" +
                                "    \"Content-Type\": \"application/json\"\n" +
                                "  },\n" +
                                "  \"json\": {\n" +
                                "    \"status\": \"updated\"\n" +
                                "  },\n" +
                                "  \"origin\": \"127.0.0.1\",\n" +
                                "  \"url\": \"" + baseUrl + "/put\"\n" +
                                "}")));
        
        // Mock SOAP service (replaces dataaccess.com)
        stubFor(post(urlEqualTo("/webservicesserver/NumberConversion.wso"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml; charset=utf-8")
                        .withBody("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                                "    <soap:Body>\n" +
                                "        <NumberToWordsResponse xmlns=\"http://www.dataaccess.com/webservicesserver/\">\n" +
                                "            <NumberToWordsResult>nine hundred and three</NumberToWordsResult>\n" +
                                "        </NumberToWordsResponse>\n" +
                                "    </soap:Body>\n" +
                                "</soap:Envelope>")));
        
        // Mock generic API endpoint
        stubFor(get(urlEqualTo("/data"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"data\": \"sample data\",\n" +
                                "  \"status\": \"success\"\n" +
                                "}")));
    }
    
    /**
     * Helper method to get the base URL for the WireMock server.
     */
    protected String getBaseUrl() {
        return baseUrl;
    }
}