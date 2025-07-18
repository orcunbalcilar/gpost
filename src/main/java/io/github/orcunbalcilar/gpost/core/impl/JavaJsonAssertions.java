package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.JsonAssertions;
import java.util.ArrayList;
import java.util.List;

/**
 * Java implementation of JsonAssertions.
 */
public class JavaJsonAssertions implements JsonAssertions {
    
    private final List<JsonAssertionRule> assertions = new ArrayList<>();
    
    @Override
    public JsonAssertions pathExists(String path) {
        assertions.add(new JsonAssertionRule("pathExists", path, null));
        return this;
    }
    
    @Override
    public JsonAssertions pathEquals(String path, Object expectedValue) {
        assertions.add(new JsonAssertionRule("pathEquals", path, expectedValue));
        return this;
    }
    
    @Override
    public JsonAssertions pathContains(String path, String expectedValue) {
        assertions.add(new JsonAssertionRule("pathContains", path, expectedValue));
        return this;
    }
    
    @Override
    public JsonAssertions arraySize(String path, int expectedSize) {
        assertions.add(new JsonAssertionRule("arraySize", path, expectedSize));
        return this;
    }
    
    /**
     * Get the configured JSON assertions.
     */
    public List<JsonAssertionRule> getAssertions() {
        return assertions;
    }
    
    /**
     * Simple JSON assertion rule holder.
     */
    public static class JsonAssertionRule {
        private final String type;
        private final String path;
        private final Object value;
        
        public JsonAssertionRule(String type, String path, Object value) {
            this.type = type;
            this.path = path;
            this.value = value;
        }
        
        public String getType() {
            return type;
        }
        
        public String getPath() {
            return path;
        }
        
        public Object getValue() {
            return value;
        }
    }
}