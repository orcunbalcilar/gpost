package io.github.orcunbalcilar.gpost.teststep

import groovy.transform.CompileStatic
import orcun.balcilar.gpost.TestItemStatus

@CompileStatic
abstract class TestStep {
    String name
    TestItemStatus status = TestItemStatus.UNKNOWN
    boolean disabled = false

    void name(String name) { this.name = name }

    abstract void run()
}