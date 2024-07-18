package io.github.orcunbalcilar.gpost.teststep.request

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
abstract class Request {
    private final Map<String, String> allHeaders = [:]
    private final List<LazyMapClosure> lazyMapClosures = []

    @Delegate
    final TestCaseRunContext context

    Request(TestCaseRunContext context) { this.context = context }

    void headers(@DelegatesTo(value = LazyMapClosure, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        LazyMapClosure lazyMapClosure = new LazyMapClosure(context, closure)
        closure.delegate = lazyMapClosure
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        lazyMapClosures << lazyMapClosure
    }

    void headers(Map<String, String> map) { allHeaders.putAll(map) }

    void header(String key, String value) { allHeaders.put(key, value) }

    Map<String, String> headers() {
        lazyMapClosures.each {
            it.run()
            allHeaders.putAll(it.result())
        }
        return allHeaders
    }
}
