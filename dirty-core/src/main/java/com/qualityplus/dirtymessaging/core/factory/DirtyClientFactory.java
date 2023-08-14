package com.qualityplus.dirtymessaging.core.factory;

import com.qualityplus.dirtymessaging.DirtyClient;
import com.qualityplus.dirtymessaging.api.builder.ClientBuilder;
import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.factory.ConnectionFactory;
import com.qualityplus.dirtymessaging.api.factory.ClientFactory;
import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.core.client.DirtyClientImpl;

import java.util.Map;
import java.util.Set;

public final class DirtyClientFactory implements ClientFactory {
    private final ConnectionFactory connectionFactory;

    public DirtyClientFactory(final ConnectionFactory connectionFactory) {
        this.connectionFactory  = connectionFactory;
    }

    public DirtyClientFactory() {
        this.connectionFactory = new DirtyConnectionFactory();
    }

    @Override
    public DirtyClient getClient(final ClientBuilder builder) {
        final DirtyConnection connection = this.connectionFactory.getConnection(builder.getCredentials());

        final DirtyClient client = new DirtyClientImpl(connection);

        setHandlers(client, builder);

        return client;
    }

    @SuppressWarnings("rawtypes")
    private void setHandlers(final DirtyClient service, final ClientBuilder builder) {
        final Map<Class, Set<DirtySubscriber>> handlers = builder.getHandlers();

        handlers.forEach((aClass, msgHandlers) -> msgHandlers.forEach(handler -> service.addSubscriber(aClass, handler)));

    }
}
