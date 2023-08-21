package com.qualityplus.fastmessaging.api.sub;

import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;

public interface FastMQSubscriber<T extends MessagePackSerializable> {
    public void accept(final T message);

    public default boolean isOneTime() {
        return false;
    }
}
