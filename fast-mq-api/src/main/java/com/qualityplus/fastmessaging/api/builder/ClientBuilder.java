package com.qualityplus.fastmessaging.api.builder;

import com.qualityplus.fastmessaging.FastMQClient;
import com.qualityplus.fastmessaging.api.credentials.Credentials;
import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public interface ClientBuilder {
    public <T extends MessagePackSerializable> ClientBuilder withSubscriber(final Class<T> clazz,
                                                                            final FastMQSubscriber<T> consumer);

    public ClientBuilder withCredentials(final Credentials credentials);

    public Map<Class, Set<FastMQSubscriber>> getHandlers();

    public Credentials getCredentials();

    public FastMQClient create();
}
