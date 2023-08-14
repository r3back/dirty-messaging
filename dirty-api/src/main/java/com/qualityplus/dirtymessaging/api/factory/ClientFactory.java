package com.qualityplus.dirtymessaging.api.factory;

import com.qualityplus.dirtymessaging.DirtyClient;
import com.qualityplus.dirtymessaging.api.builder.ClientBuilder;

public interface ClientFactory {
    public DirtyClient getClient(final ClientBuilder builder);
}
