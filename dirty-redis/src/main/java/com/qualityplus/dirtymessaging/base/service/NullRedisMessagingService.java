package com.qualityplus.dirtymessaging.base.service;

import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.base.RedisMessagingService;

public final class NullRedisMessagingService implements RedisMessagingService {
    @Override
    public void publish(MessagePackSerializable message) {

    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer) {

    }
}
