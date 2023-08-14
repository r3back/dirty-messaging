package com.qualityplus.dirtymessaging.api.sub;

import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

import java.util.*;

public final class MessageHandlerRegistry {
    public static final MessageHandlerRegistry INSTANCE = new MessageHandlerRegistry();

    @SuppressWarnings("rawtypes")
    private final Map<Class<?>, Set<DirtySubscriber>> consumerMap;

    public MessageHandlerRegistry() {
        this.consumerMap = new HashMap<>();
    }

    public <T extends MessagePackSerializable> void addHandler(final Class<T> clazz, final DirtySubscriber<T> consumer) {
        final Set<? super DirtySubscriber<?>> set = this.consumerMap.get(clazz);

        if (set == null) {
            consumerMap.put(clazz, new HashSet<>(Collections.singletonList(consumer)));
            return;
        }
        set.add(consumer);
    }

    @SuppressWarnings("unchecked")
    public void callAll(final Class<?> clazz, final MessagePackSerializable message) {
        Iterator<? super DirtySubscriber<?>> iterator = this.consumerMap.get(clazz).iterator();

        while (iterator.hasNext()) {
            DirtySubscriber<MessagePackSerializable> next = (DirtySubscriber<MessagePackSerializable>) iterator.next();
            next.accept(message);
            if (next.isOneTime()) {
                iterator.remove();
            }
        }
    }
}
