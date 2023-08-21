package com.qualityplus.fastmq.core.builder;

import com.qualityplus.fastmessaging.FastMQClient;
import com.qualityplus.fastmessaging.api.builder.ClientBuilder;
import com.qualityplus.fastmessaging.api.credentials.Credentials;
import com.qualityplus.fastmessaging.api.factory.ClientFactory;
import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.fastmq.core.factory.FastMQClientFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public final class FastMQClientBuilder implements ClientBuilder {
    private final Map<Class, Set<FastMQSubscriber>> handlers = new HashMap<>();
    private final ClientFactory clientFactory;
    private Credentials credentials;

    public FastMQClientBuilder(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public FastMQClientBuilder() {
        this.clientFactory = new FastMQClientFactory();
    }

    @Override
    public ClientBuilder withCredentials(Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    @Override
    public <T extends MessagePackSerializable> ClientBuilder withSubscriber(Class<T> clazz, FastMQSubscriber<T> consumer){
        final Set<? super FastMQSubscriber<?>> set = this.handlers.get(clazz);

        if (set == null) {
            this.handlers.put(clazz, new HashSet<>(Collections.singleton(consumer)));
            return this;
        }

        set.add(consumer);
        return this;
    }

    @Override
    public Map<Class, Set<FastMQSubscriber>> getHandlers() {
        return this.handlers;
    }

    @Override
    public Credentials getCredentials() {
        return this.credentials;
    }

    @Override
    public FastMQClient create(){
        return this.clientFactory.getClient(this);
    }
}
