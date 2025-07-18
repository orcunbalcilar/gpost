package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for XML response assertions.
 */
public interface XmlAssertions {
    
    /**
     * Assert that an XPath exists.
     * 
     * @param xpath The XPath expression
     * @return This assertions for method chaining
     */
    XmlAssertions pathExists(String xpath);
    
    /**
     * Assert that an XPath has a specific value.
     * 
     * @param xpath The XPath expression
     * @param expectedValue The expected value
     * @return This assertions for method chaining
     */
    XmlAssertions pathEquals(String xpath, String expectedValue);
    
    /**
     * Assert that an XPath contains a specific value.
     * 
     * @param xpath The XPath expression
     * @param expectedValue The expected value
     * @return This assertions for method chaining
     */
    XmlAssertions pathContains(String xpath, String expectedValue);
    
    /**
     * Assert that an XML element has a specific attribute.
     * 
     * @param xpath The XPath expression to the element
     * @param attributeName The attribute name
     * @param expectedValue The expected attribute value
     * @return This assertions for method chaining
     */
    XmlAssertions attribute(String xpath, String attributeName, String expectedValue);
}