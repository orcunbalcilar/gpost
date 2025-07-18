package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.HttpAssertions;
import io.github.orcunbalcilar.gpost.core.JsonAssertions;
import io.github.orcunbalcilar.gpost.core.XmlAssertions;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Java implementation of HttpAssertions.
 */
public class JavaHttpAssertions implements HttpAssertions {
    
    private final List<AssertionRule> assertions = new ArrayList<>();
    private final JavaJsonAssertions jsonAssertions = new JavaJsonAssertions();
    private final JavaXmlAssertions xmlAssertions = new JavaXmlAssertions();
    
    @Override
    public HttpAssertions statusCode(int expectedStatusCode) {
        assertions.add(new AssertionRule("statusCode", expectedStatusCode));
        return this;
    }
    
    @Override
    public HttpAssertions bodyContains(String text) {
        assertions.add(new AssertionRule("bodyContains", text));
        return this;
    }
    
    @Override
    public HttpAssertions bodyEquals(String text) {
        assertions.add(new AssertionRule("bodyEquals", text));
        return this;
    }
    
    @Override
    public HttpAssertions json(Consumer<JsonAssertions> config) {
        config.accept(jsonAssertions);
        return this;
    }
    
    @Override
    public HttpAssertions xml(Consumer<XmlAssertions> config) {
        config.accept(xmlAssertions);
        return this;
    }
    
    @Override
    public HttpAssertions header(String headerName, String expectedValue) {
        assertions.add(new AssertionRule("header", headerName + "=" + expectedValue));
        return this;
    }
    
    /**
     * Get the configured assertions.
     */
    public List<AssertionRule> getAssertions() {
        return assertions;
    }
    
    /**
     * Get the JSON assertions.
     */
    public JavaJsonAssertions getJsonAssertions() {
        return jsonAssertions;
    }
    
    /**
     * Get the XML assertions.
     */
    public JavaXmlAssertions getXmlAssertions() {
        return xmlAssertions;
    }
    
    /**
     * Simple assertion rule holder.
     */
    public static class AssertionRule {
        private final String type;
        private final Object value;
        
        public AssertionRule(String type, Object value) {
            this.type = type;
            this.value = value;
        }
        
        public String getType() {
            return type;
        }
        
        public Object getValue() {
            return value;
        }
    }
}