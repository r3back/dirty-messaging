package com.qualityplus.fastmessaging.api.connection;

import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;

public interface FastMQConnection {
    public void publish(final MessagePackSerializable message);

    public <T extends MessagePackSerializable> void addHandler(final Class<T> clazz, final FastMQSubscriber<T> consumer);
}
