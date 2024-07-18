package io.github.orcunbalcilar.gpost.teststep.request

import groovy.transform.CompileStatic

@CompileStatic
interface WithBody {
    String getBodyContent()
}