package com.qualityplus.dirtymessaging.base.listener;

import com.qualityplus.dirtymessaging.DirtyPubSub;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.base.util.StringByteCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public final class RedisMessagePubSubListener extends DirtyPubSub implements RedisPubSubListener<String, byte[]> {
    private final String prefix;

    public RedisMessagePubSubListener(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void message(String channel, byte[] message) {
    }

    @Override
    public void message(String pattern, String channel, byte[] message) {
        String className = channel.replace(prefix, "");

        try {
            Class<?> clazz = Class.forName(className);

            if (!MessagePackSerializable.class.isAssignableFrom(clazz)) {
                log.error("{} does not implement MessagePackSerializable", className);
                return;
            }

            MessagePackSerializable messagePackSerializable = (MessagePackSerializable) unsafe.allocateInstance(clazz);

            messagePackSerializable.deserialize(message);

            registry.callAll(clazz, messagePackSerializable);
        } catch (ClassNotFoundException | InstantiationException | IOException e) {
            log.error("error while handling message on channel {} with classname {}", channel, className, e);
        }
    }

    @Override
    public void subscribed(String channel, long count) {
    }

    @Override
    public void psubscribed(String pattern, long count) {
    }

    @Override
    public void unsubscribed(String channel, long count) {
    }

    @Override
    public void punsubscribed(String pattern, long count) {
    }

    public void register(RedisClient client) {
        StatefulRedisPubSubConnection<String, byte[]> conn = client.connectPubSub(StringByteCodec.INSTANCE);

        conn.addListener(this);

        conn.sync().psubscribe(this.prefix + "*");
    }
}