package com.qualityplus.dirtymessaging;

import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

public interface DirtyService {
    void publish(MessagePackSerializable message);

    <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer);
}
