package io.github.orcunbalcilar.gpost

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestTemplateInvocationContext
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider

import java.util.stream.Stream

class ArgumentsSourceProvider implements TestTemplateInvocationContextProvider {
    static Stream<?> STREAM = (1..100).stream()

    static <T> Stream<T> get() {
        return STREAM as Stream<T>
    }

    @Override
    boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return false
    }

    @Override
    Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        return
    }
}