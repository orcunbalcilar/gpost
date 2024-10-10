package io.github.orcunbalcilar.gpost.teststep.request.body


import groovy.transform.CompileStatic
import groovy.xml.StreamingMarkupBuilder
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext
import org.apache.hc.core5.http.ContentType

@CompileStatic
class XmlRequestBody extends RequestBody {
    final Closure closure

    XmlRequestBody(TestCaseRunContext context, Closure closure) {
        super(context)
        this.closure = closure
    }

    @Override
    ContentType getContentType() { ContentType.TEXT_XML }

    @Override
    String build() { new StreamingMarkupBuilder().bind(closure).toString() }
}
