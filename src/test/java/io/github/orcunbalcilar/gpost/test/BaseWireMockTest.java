package io.github.orcunbalcilar.gpost.test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Base test class that provides WireMock virtual services for testing.
 * This eliminates the need to rely on external APIs which can break the build process.
 */
public abstract class BaseWireMockTest {
    
    protected WireMockServer wireMockServer;
    protected String baseUrl;
    
    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        baseUrl = "http://localhost:8089";
        
        // Configure WireMock to use our server
        WireMock.configureFor("localhost", 8089);
        
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