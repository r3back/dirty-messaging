package com.qualityplus.dirtymessaging.api.handler;

import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

public interface MessageHandler<T extends MessagePackSerializable> {
    void accept(T message);

    default boolean isOneTime() {
        return false;
    }
}
