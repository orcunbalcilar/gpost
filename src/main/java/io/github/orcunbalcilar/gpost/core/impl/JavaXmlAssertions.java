package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.XmlAssertions;
import java.util.ArrayList;
import java.util.List;

/**
 * Java implementation of XmlAssertions.
 */
public class JavaXmlAssertions implements XmlAssertions {
    
    private final List<XmlAssertionRule> assertions = new ArrayList<>();
    
    @Override
    public XmlAssertions pathExists(String xpath) {
        assertions.add(new XmlAssertionRule("pathExists", xpath, null, null));
        return this;
    }
    
    @Override
    public XmlAssertions pathEquals(String xpath, String expectedValue) {
        assertions.add(new XmlAssertionRule("pathEquals", xpath, expectedValue, null));
        return this;
    }
    
    @Override
    public XmlAssertions pathContains(String xpath, String expectedValue) {
        assertions.add(new XmlAssertionRule("pathContains", xpath, expectedValue, null));
        return this;
    }
    
    @Override
    public XmlAssertions attribute(String xpath, String attributeName, String expectedValue) {
        assertions.add(new XmlAssertionRule("attribute", xpath, expectedValue, attributeName));
        return this;
    }
    
    /**
     * Get the configured XML assertions.
     */
    public List<XmlAssertionRule> getAssertions() {
        return assertions;
    }
    
    /**
     * Simple XML assertion rule holder.
     */
    public static class XmlAssertionRule {
        private final String type;
        private final String xpath;
        private final String value;
        private final String attributeName;
        
        public XmlAssertionRule(String type, String xpath, String value, String attributeName) {
            this.type = type;
            this.xpath = xpath;
            this.value = value;
            this.attributeName = attributeName;
        }
        
        public String getType() {
            return type;
        }
        
        public String getXpath() {
            return xpath;
        }
        
        public String getValue() {
            return value;
        }
        
        public String getAttributeName() {
            return attributeName;
        }
    }
}