package com.qualityplus.fastmq.core.client;

import com.qualityplus.fastmessaging.FastMQClient;
import com.qualityplus.fastmessaging.api.connection.FastMQConnection;
import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FastMQClientImpl implements FastMQClient {
    private final FastMQConnection connection;

    @Override
    public void publish(final MessagePackSerializable message) {
        this.connection.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addSubscriber(final Class<T> clazz,
                                                                  final FastMQSubscriber<T> subscriber) {
        this.connection.addHandler(clazz, subscriber);
    }
}
