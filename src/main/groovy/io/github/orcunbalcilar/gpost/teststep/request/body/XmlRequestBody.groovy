package io.github.orcunbalcilar.gpost.teststep.request.body

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.xml.MarkupBuilder
import orcun.balcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
class XmlRequestBody extends RequestBody {
    final Closure closure

    XmlRequestBody(TestCaseRunContext context, Closure closure) {
        this.context = context
        closure.delegate = context
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        this.closure = closure
    }

    @CompileDynamic
    String build() {
        MarkupBuilder xmlBuilder = new MarkupBuilder()
        xmlBuilder.root {
            closure.delegate = xmlBuilder
            closure.resolveStrategy = DELEGATE_FIRST
            closure()
        }
    }

    @Override
    TestCaseRunContext getContext() { return this.@context }
}
