package com.qualityplus.fastmq.base.connection;

import com.qualityplus.fastmessaging.api.connection.FastMQConnection;
import com.qualityplus.fastmessaging.api.credentials.Credentials;
import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmessaging.api.sub.MessageHandlerRegistry;
import com.qualityplus.fastmessaging.api.publisher.FastMQPublisher;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.fastmq.base.listener.FastRedisListener;
import com.qualityplus.fastmq.base.publisher.FastRedisPublisher;
import io.lettuce.core.RedisClient;

public final class FastRedisConnection implements FastMQConnection {
    private final MessageHandlerRegistry messageHandlerRegistry = MessageHandlerRegistry.INSTANCE;
    private final FastMQPublisher publisher;

    public static FastMQConnection of(final Credentials credentials){
        return new FastRedisConnection(credentials);
    }

    private FastRedisConnection(final Credentials credentials) {
        this.publisher = createPublisher(credentials);
    }

    @Override
    public void publish(final MessagePackSerializable message) {
        publisher.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(Class<T> clazz, FastMQSubscriber<T> consumer) {
        messageHandlerRegistry.addHandler(clazz, consumer);
    }

    private FastMQPublisher createPublisher(final Credentials credentials) {
        final String uri = credentials.getUri();

        final String prefix = credentials.getPrefix();

        final RedisClient client = RedisClient.create(uri);

        registerRedisPubSubListener(client, prefix);

        return new FastRedisPublisher(client, prefix);
    }

    private static void registerRedisPubSubListener(final RedisClient client, final String prefix) {
        final FastRedisListener listener = new FastRedisListener(prefix);

        listener.register(client);
    }
}
