package com.qualityplus.dirtymessaging.api.publisher;

import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

public interface DirtyPublisher {
    public void publish(final MessagePackSerializable message);
}
