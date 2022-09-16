package com.qualityplus.dirtymessaging.api.handler;

import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

import java.util.*;

public final class MessageHandlerRegistry {
    public static final MessageHandlerRegistry INSTANCE = new MessageHandlerRegistry();

    @SuppressWarnings("rawtypes")
    private final Map<Class<?>, Set<MessageHandler>> consumerMap;

    public MessageHandlerRegistry() {
        this.consumerMap = new HashMap<>();
    }

    public <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer) {
        Set<? super MessageHandler<?>> set = this.consumerMap.get(clazz);

        if (set == null) {
            consumerMap.put(clazz, new HashSet<>(Collections.singletonList(consumer)));
            return;
        }
        set.add(consumer);
    }

    @SuppressWarnings("unchecked")
    public void callAll(Class<?> clazz, MessagePackSerializable message) {
        Iterator<? super MessageHandler<?>> iterator = this.consumerMap.get(clazz).iterator();

        while (iterator.hasNext()) {
            MessageHandler<MessagePackSerializable> next = (MessageHandler<MessagePackSerializable>) iterator.next();
            next.accept(message);
            if (next.isOneTime()) {
                iterator.remove();
            }
        }
    }
}
