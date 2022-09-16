package com.qualityplus.dirtymessaging.api.serialization;

import java.io.IOException;

public interface MessagePackSerializable {

    byte[] serialize() throws IOException;

    void deserialize(byte[] bytes) throws IOException;
}
