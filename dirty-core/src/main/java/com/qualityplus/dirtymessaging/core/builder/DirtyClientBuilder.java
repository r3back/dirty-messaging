package com.qualityplus.dirtymessaging.core.builder;

import com.qualityplus.dirtymessaging.DirtyClient;
import com.qualityplus.dirtymessaging.api.builder.ClientBuilder;
import com.qualityplus.dirtymessaging.api.credentials.Credentials;
import com.qualityplus.dirtymessaging.api.factory.ClientFactory;
import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.core.factory.DirtyClientFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public final class DirtyClientBuilder implements ClientBuilder {
    private final Map<Class, Set<DirtySubscriber>> handlers = new HashMap<>();
    private final ClientFactory clientFactory;
    private Credentials credentials;

    public DirtyClientBuilder(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public DirtyClientBuilder() {
        this.clientFactory = new DirtyClientFactory();
    }

    @Override
    public ClientBuilder withCredentials(Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    @Override
    public <T extends MessagePackSerializable> ClientBuilder withSubscriber(Class<T> clazz, DirtySubscriber<T> consumer){
        final Set<? super DirtySubscriber<?>> set = this.handlers.get(clazz);

        if (set == null) {
            this.handlers.put(clazz, new HashSet<>(Collections.singleton(consumer)));
            return this;
        }

        set.add(consumer);
        return this;
    }

    @Override
    public Map<Class, Set<DirtySubscriber>> getHandlers() {
        return this.handlers;
    }

    @Override
    public Credentials getCredentials() {
        return this.credentials;
    }

    @Override
    public DirtyClient create(){
        return this.clientFactory.getClient(this);
    }
}
