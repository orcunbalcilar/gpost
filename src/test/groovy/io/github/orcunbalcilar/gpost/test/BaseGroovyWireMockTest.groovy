package io.github.orcunbalcilar.gpost.test

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import static com.github.tomakehurst.wiremock.client.WireMock.*

/**
 * Base Groovy test class that provides WireMock virtual services for testing.
 * This eliminates the need to rely on external APIs which can break the build process.
 */
abstract class BaseGroovyWireMockTest {
    
    protected WireMockServer wireMockServer
    protected String baseUrl
    
    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(8090)
        wireMockServer.start()
        baseUrl = "http://localhost:8090"
        
        // Configure WireMock to use our server
        WireMock.configureFor("localhost", 8090)
        
        // Set up common mock endpoints
        setupCommonMocks()
    }
    
    @AfterEach
    void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop()
        }
    }
    
    /**
     * Sets up common mock endpoints that replace external services.
     */
    protected void setupCommonMocks() {
        // Mock SOAP service (replaces dataaccess.com)
        stubFor(post(urlEqualTo("/webservicesserver/NumberConversion.wso"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml; charset=utf-8")
                        .withBody("""<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Body>
        <NumberToWordsResponse xmlns="http://www.dataaccess.com/webservicesserver/">
            <NumberToWordsResult>nine hundred and three </NumberToWordsResult>
        </NumberToWordsResponse>
    </soap:Body>
</soap:Envelope>""")))
        
        // Mock httpbin.org/get endpoint
        stubFor(get(urlEqualTo("/get"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
{
  "args": {},
  "headers": {
    "Accept": "application/json",
    "User-Agent": "Groovy-DSL-Test"
  },
  "origin": "127.0.0.1",
  "url": "${baseUrl}/get"
}
""")))
    }
    
    /**
     * Helper method to get the base URL for the WireMock server.
     */
    protected String getBaseUrl() {
        return baseUrl
    }
}