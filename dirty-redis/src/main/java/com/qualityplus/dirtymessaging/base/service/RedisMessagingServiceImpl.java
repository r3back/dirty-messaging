package com.qualityplus.dirtymessaging.base.service;

import com.qualityplus.dirtymessaging.DirtyService;
import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class RedisMessagingServiceImpl implements DirtyService {
    private final DirtyConnection connection;

    @Override
    public void publish(MessagePackSerializable message) {
        connection.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer) {
        connection.addHandler(clazz, consumer);
    }
}
