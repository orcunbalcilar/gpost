package io.github.orcunbalcilar.gpost.testcase

import groovy.transform.CompileStatic

@CompileStatic
interface ContextAccess {
    TestCaseRunContext getContext()
}
