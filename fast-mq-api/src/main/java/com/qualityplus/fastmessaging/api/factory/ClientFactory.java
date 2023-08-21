package com.qualityplus.fastmessaging.api.factory;

import com.qualityplus.fastmessaging.FastMQClient;
import com.qualityplus.fastmessaging.api.builder.ClientBuilder;

public interface ClientFactory {
    public FastMQClient getClient(final ClientBuilder builder);
}
