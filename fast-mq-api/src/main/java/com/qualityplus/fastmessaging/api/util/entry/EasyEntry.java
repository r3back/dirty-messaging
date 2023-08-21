package com.qualityplus.fastmessaging.api.util.entry;

import java.util.Map;

public final class EasyEntry {
    public static <K, V> Map.Entry<K, V> entry(K k, V v) {
        return new KeyValueHolder<>(k, v);
    }
}
