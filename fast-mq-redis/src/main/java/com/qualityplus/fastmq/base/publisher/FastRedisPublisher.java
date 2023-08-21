package com.qualityplus.fastmq.base.publisher;

import com.qualityplus.fastmessaging.api.publisher.FastMQPublisher;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.fastmq.base.util.StringByteCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public final class FastRedisPublisher implements FastMQPublisher {
    private final RedisCommands<String, byte[]> commands;
    private final String prefix;

    public FastRedisPublisher(final RedisClient client, final String prefix) {
        this.commands = client.connect(StringByteCodec.INSTANCE).sync();
        this.prefix = prefix;
    }

    @Override
    public void publish(final MessagePackSerializable message) {
        final String className = message.getClass().getName();

        try {
            this.commands.publish(this.prefix + className, message.serialize());
        } catch (IOException e) {
            log.error("error while publishing message {}", className, e);
        }
    }

}