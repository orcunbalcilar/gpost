package io.github.orcunbalcilar.gpost.test;

/**
 * Constants used across test classes to eliminate code duplication.
 */
public final class TestConstants {
    
    /**
     * WireMock server port for HTTP testing.
     */
    public static final int WIREMOCK_PORT = 8089;
    
    /**
     * Base URL for WireMock server.
     */
    public static final String WIREMOCK_BASE_URL = "http://localhost:" + WIREMOCK_PORT;
    
    /**
     * Secondary WireMock server port for SOAP testing.
     */
    public static final int WIREMOCK_SOAP_PORT = 8090;
    
    /**
     * Base URL for SOAP WireMock server.
     */
    public static final String WIREMOCK_SOAP_BASE_URL = "http://localhost:" + WIREMOCK_SOAP_PORT;
    
    // Private constructor to prevent instantiation
    private TestConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}