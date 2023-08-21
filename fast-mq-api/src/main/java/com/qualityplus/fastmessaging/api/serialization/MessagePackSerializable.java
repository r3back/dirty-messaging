package com.qualityplus.fastmessaging.api.serialization;

import java.io.IOException;

public interface MessagePackSerializable {

    public byte[] serialize() throws IOException;

    public void deserialize(final byte[] bytes) throws IOException;
}
