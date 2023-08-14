package com.qualityplus.dirtymessaging.core.client;

import com.qualityplus.dirtymessaging.DirtyClient;
import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DirtyClientImpl implements DirtyClient {
    private final DirtyConnection connection;

    @Override
    public void publish(final MessagePackSerializable message) {
        this.connection.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addSubscriber(final Class<T> clazz,
                                                                  final DirtySubscriber<T> subscriber) {
        this.connection.addHandler(clazz, subscriber);
    }
}
