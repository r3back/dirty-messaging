package com.qualityplus.fastmessaging.api.publisher;

import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;

public interface FastMQPublisher {
    public void publish(final MessagePackSerializable message);
}
