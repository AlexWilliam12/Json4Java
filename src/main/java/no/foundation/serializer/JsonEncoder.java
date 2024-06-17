package no.foundation.serializer;

import java.util.Objects;

class JsonEncoder<T> {

    private final T t;

    JsonEncoder(T t) {
        this.t = Objects.requireNonNull(t);
    }
}
