package io.github.orcunbalcilar.gpost.integration;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * Unified base test class that provides WireMock virtual services using JUnit 5 extension. This
 * eliminates duplicate WireMock setup code across test classes.
 * <p>
 * Features: - Uses default port 8080 for simplicity - Uses WireMock's built-in JUnit 5 extension -
 * Loads mappings from resources/wiremock directory - Request verification capabilities -
 * Authentication testing support - Error scenario mocking - Automatic cleanup after each test
 */
public abstract class IntegrationTest {

    /**
     * WireMock extension that handles server lifecycle automatically. Using fixed port 8080 as
     * requested and loading mappings from resources.
     */
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig()
            .port(8080)
            .usingFilesUnderClasspath("wiremock")
        )
        .configureStaticDsl(true)  // This ensures static DSL is properly configured
        .build();

    /**
     * Get the base URL for the WireMock server.
     */
    protected String getBaseUrl() {
        return wireMockExtension.baseUrl();
    }

    /**
     * Get the WireMock server port.
     */
    protected int getWireMockPort() {
        return wireMockExtension.getPort();
    }

    /**
     * Helper method to verify that a request was made to WireMock.
     */
    protected void verifyRequest(RequestPatternBuilder requestPattern) {
        wireMockExtension.verify(requestPattern);
    }

    /**
     * Helper method to verify the number of times a request was made.
     */
    protected void verifyRequest(int count, RequestPatternBuilder requestPattern) {
        wireMockExtension.verify(count, requestPattern);
    }

    /**
     * Reset all WireMock stubs and request history.
     */
    protected void resetWireMock() {
        wireMockExtension.resetRequests();
        // Note: We don't reset mappings as they are loaded from files
    }

    /**
     * Get the WireMock extension for direct DSL usage.
     */
    protected WireMockExtension getWireMockExtension() {
        return wireMockExtension;
    }
}
