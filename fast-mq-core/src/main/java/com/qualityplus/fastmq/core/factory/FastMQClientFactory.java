package com.qualityplus.fastmq.core.factory;

import com.qualityplus.fastmessaging.FastMQClient;
import com.qualityplus.fastmessaging.api.builder.ClientBuilder;
import com.qualityplus.fastmessaging.api.connection.FastMQConnection;
import com.qualityplus.fastmessaging.api.factory.ConnectionFactory;
import com.qualityplus.fastmessaging.api.factory.ClientFactory;
import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmq.core.client.FastMQClientImpl;

import java.util.Map;
import java.util.Set;

public final class FastMQClientFactory implements ClientFactory {
    private final ConnectionFactory connectionFactory;

    public FastMQClientFactory(final ConnectionFactory connectionFactory) {
        this.connectionFactory  = connectionFactory;
    }

    public FastMQClientFactory() {
        this.connectionFactory = new FastMQConnectionFactory();
    }

    @Override
    public FastMQClient getClient(final ClientBuilder builder) {
        final FastMQConnection connection = this.connectionFactory.getConnection(builder.getCredentials());

        final FastMQClient client = new FastMQClientImpl(connection);

        setHandlers(client, builder);

        return client;
    }

    @SuppressWarnings("rawtypes")
    private void setHandlers(final FastMQClient service, final ClientBuilder builder) {
        final Map<Class, Set<FastMQSubscriber>> handlers = builder.getHandlers();

        handlers.forEach((aClass, msgHandlers) -> msgHandlers.forEach(handler -> service.addSubscriber(aClass, handler)));

    }
}
