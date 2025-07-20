package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.impl.XmlAssertionsImpl.XmlAssertionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for XmlAssertionsImpl.
 * Tests XML assertion functionality including XPath-based assertions.
 */
class XmlAssertionsImplTest {

    private XmlAssertionsImpl xmlAssertions;

    @BeforeEach
    void setUp() {
        xmlAssertions = new XmlAssertionsImpl();
    }

    @Test
    void shouldCreateEmptyAssertions() {
        // When
        XmlAssertionsImpl assertions = new XmlAssertionsImpl();

        // Then
        assertNotNull(assertions);
        assertTrue(assertions.getAssertions().isEmpty());
    }

    @Test
    void shouldAddPathExistsAssertion() {
        // Given
        String xpath = "//user/name";

        // When
        XmlAssertionsImpl result = (XmlAssertionsImpl) xmlAssertions.pathExists(xpath);

        // Then
        assertSame(xmlAssertions, result); // Should return same instance for fluent API
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("pathExists", rule.getType());
        assertEquals(xpath, rule.getXpath());
        assertNull(rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldAddPathEqualsAssertion() {
        // Given
        String xpath = "//user/age";
        String expectedValue = "25";

        // When
        XmlAssertionsImpl result = (XmlAssertionsImpl) xmlAssertions.pathEquals(xpath, expectedValue);

        // Then
        assertSame(xmlAssertions, result);
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("pathEquals", rule.getType());
        assertEquals(xpath, rule.getXpath());
        assertEquals(expectedValue, rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldAddPathContainsAssertion() {
        // Given
        String xpath = "//user/description";
        String expectedValue = "developer";

        // When
        XmlAssertionsImpl result = (XmlAssertionsImpl) xmlAssertions.pathContains(xpath, expectedValue);

        // Then
        assertSame(xmlAssertions, result);
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("pathContains", rule.getType());
        assertEquals(xpath, rule.getXpath());
        assertEquals(expectedValue, rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldAddAttributeAssertion() {
        // Given
        String xpath = "//user";
        String attributeName = "id";
        String expectedValue = "123";

        // When
        XmlAssertionsImpl result = (XmlAssertionsImpl) xmlAssertions.attribute(xpath, attributeName, expectedValue);

        // Then
        assertSame(xmlAssertions, result);
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("attribute", rule.getType());
        assertEquals(xpath, rule.getXpath());
        assertEquals(expectedValue, rule.getValue());
        assertEquals(attributeName, rule.getAttributeName());
    }

    @Test
    void shouldChainMultipleAssertions() {
        // When
        XmlAssertionsImpl result = (XmlAssertionsImpl) xmlAssertions
            .pathExists("//user")
            .pathEquals("//user/name", "John")
            .pathContains("//user/email", "@example.com")
            .attribute("//user", "status", "active");

        // Then
        assertSame(xmlAssertions, result);
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(4, assertions.size());
        
        // Verify first assertion
        XmlAssertionRule rule1 = assertions.get(0);
        assertEquals("pathExists", rule1.getType());
        assertEquals("//user", rule1.getXpath());
        assertNull(rule1.getValue());
        assertNull(rule1.getAttributeName());
        
        // Verify second assertion
        XmlAssertionRule rule2 = assertions.get(1);
        assertEquals("pathEquals", rule2.getType());
        assertEquals("//user/name", rule2.getXpath());
        assertEquals("John", rule2.getValue());
        assertNull(rule2.getAttributeName());
        
        // Verify third assertion
        XmlAssertionRule rule3 = assertions.get(2);
        assertEquals("pathContains", rule3.getType());
        assertEquals("//user/email", rule3.getXpath());
        assertEquals("@example.com", rule3.getValue());
        assertNull(rule3.getAttributeName());
        
        // Verify fourth assertion
        XmlAssertionRule rule4 = assertions.get(3);
        assertEquals("attribute", rule4.getType());
        assertEquals("//user", rule4.getXpath());
        assertEquals("active", rule4.getValue());
        assertEquals("status", rule4.getAttributeName());
    }

    @Test
    void shouldHandleNullXPath() {
        // When
        xmlAssertions.pathExists(null);

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("pathExists", rule.getType());
        assertNull(rule.getXpath());
        assertNull(rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldHandleEmptyXPath() {
        // When
        xmlAssertions.pathExists("");

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("pathExists", rule.getType());
        assertEquals("", rule.getXpath());
        assertNull(rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldHandleNullValue() {
        // When
        xmlAssertions.pathEquals("//user/name", null);

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("pathEquals", rule.getType());
        assertEquals("//user/name", rule.getXpath());
        assertNull(rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldHandleEmptyValue() {
        // When
        xmlAssertions.pathEquals("//user/name", "");

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("pathEquals", rule.getType());
        assertEquals("//user/name", rule.getXpath());
        assertEquals("", rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldHandleComplexXPaths() {
        // When
        xmlAssertions
            .pathExists("//user[@id='123']/profile/settings")
            .pathEquals("//users/user[1]/name", "First User")
            .pathContains("//data/items[@type='premium']/description", "advanced")
            .attribute("//document/metadata[@version='1.0']", "created", "2024-01-01");

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(4, assertions.size());
        
        assertEquals("//user[@id='123']/profile/settings", assertions.get(0).getXpath());
        assertEquals("//users/user[1]/name", assertions.get(1).getXpath());
        assertEquals("//data/items[@type='premium']/description", assertions.get(2).getXpath());
        assertEquals("//document/metadata[@version='1.0']", assertions.get(3).getXpath());
    }

    @Test
    void shouldHandleNullAttributeName() {
        // When
        xmlAssertions.attribute("//user", null, "value");

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("attribute", rule.getType());
        assertEquals("//user", rule.getXpath());
        assertEquals("value", rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldHandleEmptyAttributeName() {
        // When
        xmlAssertions.attribute("//user", "", "value");

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        
        XmlAssertionRule rule = assertions.get(0);
        assertEquals("attribute", rule.getType());
        assertEquals("//user", rule.getXpath());
        assertEquals("value", rule.getValue());
        assertEquals("", rule.getAttributeName());
    }

    @Test
    void shouldHandleSpecialCharactersInXPath() {
        // Given
        String xpathWithSpecialChars = "//user[@name='John O\\'Connor']/profile";
        
        // When
        xmlAssertions.pathExists(xpathWithSpecialChars);

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        assertEquals(xpathWithSpecialChars, assertions.get(0).getXpath());
    }

    @Test
    void shouldHandleNamespaceInXPath() {
        // Given
        String namespaceXPath = "//ns:user/ns:profile[@ns:type='admin']";
        
        // When
        xmlAssertions.pathExists(namespaceXPath);

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        assertEquals(namespaceXPath, assertions.get(0).getXpath());
    }

    @Test
    void shouldHandleUnicodeInValues() {
        // Given
        String unicodeValue = "Hello 世界 🌍";
        
        // When
        xmlAssertions.pathEquals("//message", unicodeValue);

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        assertEquals(unicodeValue, assertions.get(0).getValue());
    }

    @Test
    void shouldMaintainAssertionOrder() {
        // When
        xmlAssertions
            .pathExists("//first")
            .pathEquals("//second", "value2")
            .pathContains("//third", "value3")
            .attribute("//fourth", "attr", "value4");

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(4, assertions.size());
        
        assertEquals("pathExists", assertions.get(0).getType());
        assertEquals("pathEquals", assertions.get(1).getType());
        assertEquals("pathContains", assertions.get(2).getType());
        assertEquals("attribute", assertions.get(3).getType());
    }

    @Test
    void shouldHandleMultipleAttributeAssertions() {
        // When
        xmlAssertions
            .attribute("//user", "id", "123")
            .attribute("//user", "status", "active")
            .attribute("//user", "role", "admin");

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(3, assertions.size());
        
        // Verify all are attribute assertions
        assertions.forEach(rule -> assertEquals("attribute", rule.getType()));
        
        // Verify attribute names and values
        assertEquals("id", assertions.get(0).getAttributeName());
        assertEquals("123", assertions.get(0).getValue());
        assertEquals("status", assertions.get(1).getAttributeName());
        assertEquals("active", assertions.get(1).getValue());
        assertEquals("role", assertions.get(2).getAttributeName());
        assertEquals("admin", assertions.get(2).getValue());
    }

    @Test
    void shouldCreateXmlAssertionRule() {
        // When
        XmlAssertionRule rule = new XmlAssertionRule("testType", "//test/path", "testValue", "testAttribute");

        // Then
        assertNotNull(rule);
        assertEquals("testType", rule.getType());
        assertEquals("//test/path", rule.getXpath());
        assertEquals("testValue", rule.getValue());
        assertEquals("testAttribute", rule.getAttributeName());
    }

    @Test
    void shouldCreateXmlAssertionRuleWithNulls() {
        // When
        XmlAssertionRule rule = new XmlAssertionRule(null, null, null, null);

        // Then
        assertNotNull(rule);
        assertNull(rule.getType());
        assertNull(rule.getXpath());
        assertNull(rule.getValue());
        assertNull(rule.getAttributeName());
    }

    @Test
    void shouldHandleXPathWithPredicates() {
        // Given
        String complexXPath = "//book[position()>1 and price<10.00]/title";
        
        // When
        xmlAssertions.pathExists(complexXPath);

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        assertEquals(complexXPath, assertions.get(0).getXpath());
    }

    @Test
    void shouldHandleXPathWithFunctions() {
        // Given
        String functionXPath = "//user[contains(@name, 'John')]/profile";
        
        // When
        xmlAssertions.pathContains(functionXPath, "developer");

        // Then
        List<XmlAssertionRule> assertions = xmlAssertions.getAssertions();
        assertEquals(1, assertions.size());
        assertEquals(functionXPath, assertions.get(0).getXpath());
        assertEquals("developer", assertions.get(0).getValue());
    }
}
