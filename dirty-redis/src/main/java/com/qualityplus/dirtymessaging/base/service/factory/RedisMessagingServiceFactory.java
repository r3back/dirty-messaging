package com.qualityplus.dirtymessaging.base.service.factory;

import com.qualityplus.dirtymessaging.DirtyService;
import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.DirtyCredentials;

import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.base.connection.factory.RedisMessagingConnectionFactory;
import com.qualityplus.dirtymessaging.base.service.NullRedisMessagingService;
import com.qualityplus.dirtymessaging.base.service.RedisMessagingServiceImpl;
import com.qualityplus.dirtymessaging.base.connection.RedisMessagingConnection;

import java.util.*;

public final class RedisMessagingServiceFactory {
    @SuppressWarnings("rawtypes")
    private final Map<Class, Set<MessageHandler>> consumerMap = new HashMap<>();
    private final DirtyConnection dirtyConnection;

    private RedisMessagingServiceFactory(DirtyConnection dirtyConnection){
        this.dirtyConnection = dirtyConnection;
    }

    public static RedisMessagingServiceFactory withCredentials(DirtyCredentials credentials){
        DirtyConnection connection = RedisMessagingConnectionFactory.getConnection(credentials);

        return new RedisMessagingServiceFactory(connection);
    }

    public <T extends MessagePackSerializable> RedisMessagingServiceFactory withHandler(Class<T> clazz, MessageHandler<T> consumer){
        Set<? super MessageHandler<?>> set = this.consumerMap.get(clazz);

        if (set == null) {
            consumerMap.put(clazz, new HashSet<>(Collections.singletonList(consumer)));
            return this;
        }
        set.add(consumer);

        return this;
    }

    public DirtyService create(){
        DirtyService service = serviceFromConnection(dirtyConnection);

        consumerMap.forEach((aClass, handlers) -> handlers.forEach(handler -> service.addHandler(aClass, handler)));

        return service;
    }

    private static DirtyService serviceFromConnection(DirtyConnection connection){
        return connection instanceof RedisMessagingConnection ? new RedisMessagingServiceImpl(connection) : new NullRedisMessagingService();
    }

}
