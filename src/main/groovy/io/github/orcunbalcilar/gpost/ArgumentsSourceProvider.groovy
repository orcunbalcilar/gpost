package io.github.orcunbalcilar.gpost

import java.util.stream.Stream

class ArgumentsSourceProvider {
    static Stream<?> STREAM = (1..200).stream()

    static <T> Stream<T> get() {
        return STREAM as Stream<T>
    }
}