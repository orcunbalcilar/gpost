package orcun.balcilar.odapi.testcases.impl

class TestCaseRunContext {
    private final Map<String, ?> props = [:]

    void context(String name, Object value) {
        props.put(name, value)
    }

    def context(String name) {
        props.get(name)
    }
}