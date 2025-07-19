package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.github.orcunbalcilar.gpost.core.impl.TestCaseRunContextImpl

@CompileStatic
@Slf4j
class TestCaseRunContext extends TestCaseRunContextImpl {

    void property(String key, Object value) {
        log.info("Setting property $key to $value")
        setProperty(key, value) // Delegate to parent Java implementation
    }

    <T> T property(String key) { 
        getProperty(key) as T 
    }

    void setAt(String key, Object value) { 
        property(key, value) 
    }

    <T> T getAt(String key) { 
        getProperty(key) as T 
    }
}