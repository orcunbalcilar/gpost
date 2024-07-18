package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@CompileStatic
@Slf4j
class TestCaseRunContext {
    private final Map<String, Object> parameters = [:]

    void property(String key, Object value) {
        log.info("Setting property $key to $value")
        parameters[key] = value
    }

    @Override
    Object getProperty(String propertyName) { parameters[propertyName] }

    @Override
    void setProperty(String propertyName, Object newValue) { parameters[propertyName] = newValue }

    <T> T property(String key) { getProperty(key) as T }

    void setAt(String key, Object value) { property(key, value) }

    <T> T getAt(String key) { getProperty(key) as T }
}