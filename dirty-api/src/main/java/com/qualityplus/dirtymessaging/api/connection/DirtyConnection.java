package com.qualityplus.dirtymessaging.api.connection;

import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

public interface DirtyConnection {
    public void publish(final MessagePackSerializable message);

    public <T extends MessagePackSerializable> void addHandler(final Class<T> clazz, final DirtySubscriber<T> consumer);
}
