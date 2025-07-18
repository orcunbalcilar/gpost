package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.ResponseBodyAssertions;
import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import java.util.regex.Pattern;

/**
 * Implementation of ResponseBodyAssertions.
 */
public class ResponseBodyAssertionsImpl implements ResponseBodyAssertions {
    
    private final Object actual;
    private final TestCaseRunContext context;
    
    public ResponseBodyAssertionsImpl(Object actual, TestCaseRunContext context) {
        this.actual = actual;
        this.context = context;
    }
    
    @Override
    public ResponseBodyAssertions equalsTo(Object expected) {
        if (!java.util.Objects.equals(actual, expected)) {
            throw new AssertionError("Expected: " + expected + ", but was: " + actual);
        }
        return this;
    }
    
    @Override
    public ResponseBodyAssertions contains(String text) {
        String actualStr = actual != null ? actual.toString() : "";
        if (!actualStr.contains(text)) {
            throw new AssertionError("Expected response body to contain: " + text + ", but was: " + actualStr);
        }
        return this;
    }
    
    @Override
    public ResponseBodyAssertions startsWith(String text) {
        String actualStr = actual != null ? actual.toString() : "";
        if (!actualStr.startsWith(text)) {
            throw new AssertionError("Expected response body to start with: " + text + ", but was: " + actualStr);
        }
        return this;
    }
    
    @Override
    public ResponseBodyAssertions endsWith(String text) {
        String actualStr = actual != null ? actual.toString() : "";
        if (!actualStr.endsWith(text)) {
            throw new AssertionError("Expected response body to end with: " + text + ", but was: " + actualStr);
        }
        return this;
    }
    
    @Override
    public ResponseBodyAssertions matches(String pattern) {
        String actualStr = actual != null ? actual.toString() : "";
        if (!Pattern.matches(pattern, actualStr)) {
            throw new AssertionError("Expected response body to match pattern: " + pattern + ", but was: " + actualStr);
        }
        return this;
    }
    
    @Override
    public ResponseBodyAssertions size(int expectedSize) {
        String actualStr = actual != null ? actual.toString() : "";
        if (actualStr.length() != expectedSize) {
            throw new AssertionError("Expected response body size: " + expectedSize + ", but was: " + actualStr.length());
        }
        return this;
    }
    
    @Override
    public ResponseBodyAssertions isEmpty() {
        String actualStr = actual != null ? actual.toString() : "";
        if (!actualStr.isEmpty()) {
            throw new AssertionError("Expected response body to be empty, but was: " + actualStr);
        }
        return this;
    }
    
    @Override
    public ResponseBodyAssertions isNotEmpty() {
        String actualStr = actual != null ? actual.toString() : "";
        if (actualStr.isEmpty()) {
            throw new AssertionError("Expected response body to not be empty");
        }
        return this;
    }
    
    @Override
    public Object getActual() {
        return actual;
    }
}