package orcun.balcilar.odapi.testcases.impl

import groovy.transform.CompileStatic

@CompileStatic
abstract class TestStep {
    String name
    TestItemStatus status = TestItemStatus.UNKNOWN

    abstract void run()
}