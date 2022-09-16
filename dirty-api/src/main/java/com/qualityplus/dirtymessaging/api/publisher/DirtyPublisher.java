package com.qualityplus.dirtymessaging.api.publisher;

import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

public interface DirtyPublisher {
    void publish(MessagePackSerializable message);
}
