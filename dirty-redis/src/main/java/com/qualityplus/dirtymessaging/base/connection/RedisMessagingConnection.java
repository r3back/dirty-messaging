package com.qualityplus.dirtymessaging.base.connection;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.Credentials;
import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.api.sub.MessageHandlerRegistry;
import com.qualityplus.dirtymessaging.api.publisher.DirtyPublisher;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.base.listener.DirtyRedisSubListener;
import com.qualityplus.dirtymessaging.base.publisher.DirtyRedisPublisher;
import io.lettuce.core.RedisClient;

public final class RedisMessagingConnection implements DirtyConnection {
    private final MessageHandlerRegistry messageHandlerRegistry = MessageHandlerRegistry.INSTANCE;
    private final DirtyPublisher publisher;

    public static DirtyConnection of(final Credentials credentials){
        return new RedisMessagingConnection(credentials);
    }

    private RedisMessagingConnection(final Credentials credentials) {
        this.publisher = createPublisher(credentials);
    }

    @Override
    public void publish(final MessagePackSerializable message) {
        publisher.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(Class<T> clazz, DirtySubscriber<T> consumer) {
        messageHandlerRegistry.addHandler(clazz, consumer);
    }

    private DirtyPublisher createPublisher(final Credentials credentials) {
        final String uri = credentials.getUri();

        final String prefix = credentials.getPrefix();

        final RedisClient client = RedisClient.create(uri);

        registerRedisPubSubListener(client, prefix);

        return new DirtyRedisPublisher(client, prefix);
    }

    private static void registerRedisPubSubListener(final RedisClient client, final String prefix) {
        final DirtyRedisSubListener listener = new DirtyRedisSubListener(prefix);

        listener.register(client);
    }
}
