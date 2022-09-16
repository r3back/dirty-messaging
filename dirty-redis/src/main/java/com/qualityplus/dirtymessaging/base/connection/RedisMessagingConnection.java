package com.qualityplus.dirtymessaging.base.connection;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.DirtyCredentials;
import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.handler.MessageHandlerRegistry;
import com.qualityplus.dirtymessaging.api.publisher.DirtyPublisher;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.base.listener.RedisMessagePubSubListener;
import com.qualityplus.dirtymessaging.base.publisher.RedisMessagePublisher;
import io.lettuce.core.RedisClient;

public final class RedisMessagingConnection implements DirtyConnection {
    private final MessageHandlerRegistry messageHandlerRegistry = MessageHandlerRegistry.INSTANCE;
    private final DirtyPublisher publisher;

    public static DirtyConnection of(DirtyCredentials credentials){
        return new RedisMessagingConnection(credentials);
    }

    private RedisMessagingConnection(DirtyCredentials credentials) {
        this.publisher = createPublisher(credentials);
    }

    @Override
    public void publish(MessagePackSerializable message) {
        publisher.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer) {
        messageHandlerRegistry.addHandler(clazz, consumer);
    }

    private DirtyPublisher createPublisher(DirtyCredentials credentials){
        String uri = credentials.getUri();

        String prefix = credentials.getPrefix();

        RedisClient client = RedisClient.create(uri);

        registerRedisPubSubListener(client, prefix);

        return new RedisMessagePublisher(client, prefix);
    }

    private static void registerRedisPubSubListener(RedisClient client, String prefix) {
        RedisMessagePubSubListener listener = new RedisMessagePubSubListener(prefix);

        listener.register(client);
    }
}
