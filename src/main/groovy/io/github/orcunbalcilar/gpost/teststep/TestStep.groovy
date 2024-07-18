package io.github.orcunbalcilar.gpost.teststep

import groovy.transform.CompileStatic
import io.github.orcunbalcilar.gpost.TestItemStatus

@CompileStatic
abstract class TestStep {
    String name
    TestItemStatus status = TestItemStatus.UNKNOWN
    boolean disabled = false

    void name(String name) { this.name = name }

    abstract void run()
}