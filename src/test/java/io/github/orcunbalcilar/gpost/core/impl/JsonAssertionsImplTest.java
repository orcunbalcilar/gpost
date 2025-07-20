package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.impl.JsonAssertionsImpl.JsonAssertionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JsonAssertionsImpl.
 * Tests JSON assertion functionality including path-based assertions.
 */
class JsonAssertionsImplTest {

    private JsonAssertionsImpl jsonAssertions;

    @BeforeEach
    void setUp() {
        jsonAssertions = new JsonAssertionsImpl();
    }

    @Test
    void shouldCreateEmptyAssertions() {
        // When
        JsonAssertionsImpl assertions = new JsonAssertionsImpl();

        // Then
        assertNotNull(assertions);
        assertTrue(assertions.getAssertions().isEmpty());
    }

    @Test
    void shouldAddPathExistsAssertion() {
        // Given
        String path = "$.user.name";

        // When
        JsonAssertionsImpl result = (JsonAssertionsImpl) jsonAssertions.pathExists(path);

        // Then
        assertSame(jsonAssertions, result); // Should return same instance for fluent API
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("pathExists", rule.getType());
        assertEquals(path, rule.getPath());
        assertNull(rule.getValue());
    }

    @Test
    void shouldAddPathEqualsAssertion() {
        // Given
        String path = "$.user.age";
        Integer expectedValue = 25;

        // When
        JsonAssertionsImpl result = (JsonAssertionsImpl) jsonAssertions.pathEquals(path, expectedValue);

        // Then
        assertSame(jsonAssertions, result);
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("pathEquals", rule.getType());
        assertEquals(path, rule.getPath());
        assertEquals(expectedValue, rule.getValue());
    }

    @Test
    void shouldAddPathContainsAssertion() {
        // Given
        String path = "$.user.description";
        String expectedValue = "developer";

        // When
        JsonAssertionsImpl result = (JsonAssertionsImpl) jsonAssertions.pathContains(path, expectedValue);

        // Then
        assertSame(jsonAssertions, result);
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("pathContains", rule.getType());
        assertEquals(path, rule.getPath());
        assertEquals(expectedValue, rule.getValue());
    }

    @Test
    void shouldAddArraySizeAssertion() {
        // Given
        String path = "$.users";
        int expectedSize = 5;

        // When
        JsonAssertionsImpl result = (JsonAssertionsImpl) jsonAssertions.arraySize(path, expectedSize);

        // Then
        assertSame(jsonAssertions, result);
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("arraySize", rule.getType());
        assertEquals(path, rule.getPath());
        assertEquals(expectedSize, rule.getValue());
    }

    @Test
    void shouldChainMultipleAssertions() {
        // When
        JsonAssertionsImpl result = (JsonAssertionsImpl) jsonAssertions
            .pathExists("$.user")
            .pathEquals("$.user.name", "John")
            .pathContains("$.user.email", "@example.com")
            .arraySize("$.user.roles", 3);

        // Then
        assertSame(jsonAssertions, result);
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(4, assertions.size());
        
        // Verify first assertion
        JsonAssertionRule rule1 = assertions.get(0);
        assertEquals("pathExists", rule1.getType());
        assertEquals("$.user", rule1.getPath());
        assertNull(rule1.getValue());
        
        // Verify second assertion
        JsonAssertionRule rule2 = assertions.get(1);
        assertEquals("pathEquals", rule2.getType());
        assertEquals("$.user.name", rule2.getPath());
        assertEquals("John", rule2.getValue());
        
        // Verify third assertion
        JsonAssertionRule rule3 = assertions.get(2);
        assertEquals("pathContains", rule3.getType());
        assertEquals("$.user.email", rule3.getPath());
        assertEquals("@example.com", rule3.getValue());
        
        // Verify fourth assertion
        JsonAssertionRule rule4 = assertions.get(3);
        assertEquals("arraySize", rule4.getType());
        assertEquals("$.user.roles", rule4.getPath());
        assertEquals(3, rule4.getValue());
    }

    @Test
    void shouldHandleNullPath() {
        // When
        jsonAssertions.pathExists(null);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("pathExists", rule.getType());
        assertNull(rule.getPath());
        assertNull(rule.getValue());
    }

    @Test
    void shouldHandleEmptyPath() {
        // When
        jsonAssertions.pathExists("");

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("pathExists", rule.getType());
        assertEquals("", rule.getPath());
        assertNull(rule.getValue());
    }

    @Test
    void shouldHandleNullValue() {
        // When
        jsonAssertions.pathEquals("$.user.name", null);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("pathEquals", rule.getType());
        assertEquals("$.user.name", rule.getPath());
        assertNull(rule.getValue());
    }

    @Test
    void shouldHandleDifferentValueTypes() {
        // When
        jsonAssertions
            .pathEquals("$.user.name", "John Doe")
            .pathEquals("$.user.age", 30)
            .pathEquals("$.user.active", true)
            .pathEquals("$.user.score", 95.5);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(4, assertions.size());
        
        assertEquals("John Doe", assertions.get(0).getValue());
        assertEquals(30, assertions.get(1).getValue());
        assertEquals(true, assertions.get(2).getValue());
        assertEquals(95.5, assertions.get(3).getValue());
    }

    @Test
    void shouldHandleComplexJsonPaths() {
        // When
        jsonAssertions
            .pathExists("$['user']['profile']['settings']")
            .pathEquals("$.users[0].name", "First User")
            .pathContains("$.data.items[*].type", "premium")
            .arraySize("$.results.filtered[?(@.active == true)]", 10);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(4, assertions.size());
        
        assertEquals("$['user']['profile']['settings']", assertions.get(0).getPath());
        assertEquals("$.users[0].name", assertions.get(1).getPath());
        assertEquals("$.data.items[*].type", assertions.get(2).getPath());
        assertEquals("$.results.filtered[?(@.active == true)]", assertions.get(3).getPath());
    }

    @Test
    void shouldHandleZeroArraySize() {
        // When
        jsonAssertions.arraySize("$.empty_array", 0);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("arraySize", rule.getType());
        assertEquals(0, rule.getValue());
    }

    @Test
    void shouldHandleNegativeArraySize() {
        // When
        jsonAssertions.arraySize("$.array", -1);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("arraySize", rule.getType());
        assertEquals(-1, rule.getValue());
    }

    @Test
    void shouldHandleEmptyStringInPathContains() {
        // When
        jsonAssertions.pathContains("$.user.name", "");

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        JsonAssertionRule rule = assertions.get(0);
        assertEquals("pathContains", rule.getType());
        assertEquals("", rule.getValue());
    }

    @Test
    void shouldHandleSpecialCharactersInPath() {
        // Given
        String pathWithSpecialChars = "$.data['user-info'].profile_settings";
        
        // When
        jsonAssertions.pathExists(pathWithSpecialChars);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        assertEquals(pathWithSpecialChars, assertions.get(0).getPath());
    }

    @Test
    void shouldHandleUnicodeInValues() {
        // Given
        String unicodeValue = "Hello 世界 🌍";
        
        // When
        jsonAssertions.pathEquals("$.message", unicodeValue);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(1, assertions.size());
        assertEquals(unicodeValue, assertions.get(0).getValue());
    }

    @Test
    void shouldMaintainAssertionOrder() {
        // When
        jsonAssertions
            .pathExists("$.first")
            .pathEquals("$.second", "value2")
            .pathContains("$.third", "value3")
            .arraySize("$.fourth", 4);

        // Then
        List<JsonAssertionRule> assertions = jsonAssertions.getAssertions();
        assertEquals(4, assertions.size());
        
        assertEquals("pathExists", assertions.get(0).getType());
        assertEquals("pathEquals", assertions.get(1).getType());
        assertEquals("pathContains", assertions.get(2).getType());
        assertEquals("arraySize", assertions.get(3).getType());
    }

    @Test
    void shouldCreateJsonAssertionRule() {
        // When
        JsonAssertionRule rule = new JsonAssertionRule("testType", "$.test.path", "testValue");

        // Then
        assertNotNull(rule);
        assertEquals("testType", rule.getType());
        assertEquals("$.test.path", rule.getPath());
        assertEquals("testValue", rule.getValue());
    }

    @Test
    void shouldCreateJsonAssertionRuleWithNulls() {
        // When
        JsonAssertionRule rule = new JsonAssertionRule(null, null, null);

        // Then
        assertNotNull(rule);
        assertNull(rule.getType());
        assertNull(rule.getPath());
        assertNull(rule.getValue());
    }
}
