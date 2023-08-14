package com.qualityplus.dirtymessaging.api.builder;

import com.qualityplus.dirtymessaging.DirtyClient;
import com.qualityplus.dirtymessaging.api.credentials.Credentials;
import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public interface ClientBuilder {
    public <T extends MessagePackSerializable> ClientBuilder withSubscriber(final Class<T> clazz,
                                                                            final DirtySubscriber<T> consumer);

    public ClientBuilder withCredentials(final Credentials credentials);

    public Map<Class, Set<DirtySubscriber>> getHandlers();

    public Credentials getCredentials();

    public DirtyClient create();
}
