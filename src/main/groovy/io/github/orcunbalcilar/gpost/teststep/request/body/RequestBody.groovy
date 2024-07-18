package io.github.orcunbalcilar.gpost.teststep.request.body

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.testcase.ContextAccess
import io.github.orcunbalcilar.gpost.testcase.TestCaseRunContext

@CompileStatic
abstract class RequestBody implements ContextAccess {
    protected TestCaseRunContext context

    abstract String build()
}
