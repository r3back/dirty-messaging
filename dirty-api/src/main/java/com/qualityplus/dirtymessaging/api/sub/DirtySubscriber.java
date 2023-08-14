package com.qualityplus.dirtymessaging.api.sub;

import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

public interface DirtySubscriber<T extends MessagePackSerializable> {
    public void accept(final T message);

    public default boolean isOneTime() {
        return false;
    }
}
