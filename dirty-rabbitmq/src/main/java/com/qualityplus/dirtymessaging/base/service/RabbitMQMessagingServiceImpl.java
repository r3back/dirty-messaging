package com.qualityplus.dirtymessaging.base.service;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.handler.MessageHandlerRegistry;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.base.RabbitMQMessagingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class RabbitMQMessagingServiceImpl implements RabbitMQMessagingService {
    private final MessageHandlerRegistry messageHandlerRegistry = MessageHandlerRegistry.INSTANCE;
    private final DirtyConnection connection;

    @Override
    public void publish(MessagePackSerializable message) {
        connection.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer) {
        messageHandlerRegistry.addHandler(clazz, consumer);
    }
}
