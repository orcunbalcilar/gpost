package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.core.TestCaseRunContext;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of TestCaseRunContext.
 */
public class TestCaseRunContextImpl implements TestCaseRunContext {
    
    private static final Logger logger = LoggerFactory.getLogger(TestCaseRunContextImpl.class);
    
    private final Map<String, Object> properties = new HashMap<>();
    
    @Override
    public void setProperty(String key, Object value) {
        logger.info("Setting property " + key + " to " + value);
        properties.put(key, value);
    }
    
    @Override
    public Object getProperty(String key) {
        return properties.get(key);
    }
    
    @Override
    public <T> T getProperty(String key, Class<T> type) {
        Object value = properties.get(key);
        if (value == null) {
            return null;
        }
        return type.cast(value);
    }
    
    @Override
    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }
    
    @Override
    public Object removeProperty(String key) {
        return properties.remove(key);
    }
    
    @Override
    public void clearProperties() {
        properties.clear();
    }
}