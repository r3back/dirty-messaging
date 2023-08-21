package com.qualityplus.fastmessaging.api.sub;

import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;

import java.util.*;

public final class MessageHandlerRegistry {
    public static final MessageHandlerRegistry INSTANCE = new MessageHandlerRegistry();

    @SuppressWarnings("rawtypes")
    private final Map<Class<?>, Set<FastMQSubscriber>> consumerMap;

    public MessageHandlerRegistry() {
        this.consumerMap = new HashMap<>();
    }

    public <T extends MessagePackSerializable> void addHandler(final Class<T> clazz, final FastMQSubscriber<T> consumer) {
        final Set<? super FastMQSubscriber<?>> set = this.consumerMap.get(clazz);

        if (set == null) {
            consumerMap.put(clazz, new HashSet<>(Collections.singletonList(consumer)));
            return;
        }
        set.add(consumer);
    }

    @SuppressWarnings("unchecked")
    public void callAll(final Class<?> clazz, final MessagePackSerializable message) {
        Iterator<? super FastMQSubscriber<?>> iterator = this.consumerMap.get(clazz).iterator();

        while (iterator.hasNext()) {
            FastMQSubscriber<MessagePackSerializable> next = (FastMQSubscriber<MessagePackSerializable>) iterator.next();
            next.accept(message);
            if (next.isOneTime()) {
                iterator.remove();
            }
        }
    }
}
