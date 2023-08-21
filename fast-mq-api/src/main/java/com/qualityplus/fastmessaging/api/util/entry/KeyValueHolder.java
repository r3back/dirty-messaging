package com.qualityplus.fastmessaging.api.util.entry;

import java.util.Map;
import java.util.Objects;

public final class KeyValueHolder<K, V> implements Map.Entry<K, V> {
    private final K key;
    private final V value;

    public KeyValueHolder(final K k, final V v) {
        this.key = Objects.requireNonNull(k);
        this.value = Objects.requireNonNull(v);
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(final V value) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        final Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
        return this.key.equals(e.getKey()) && this.value.equals(e.getValue());
    }

    @Override
    public int hashCode() {
        return this.key.hashCode() ^ this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.key + "=" + this.value;
    }
}
