package com.qualityplus.dirtymessaging.api.connection;

import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

public interface DirtyConnection {
    void publish(MessagePackSerializable message);

    <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer);
}
