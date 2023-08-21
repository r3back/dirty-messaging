package com.qualityplus.fastmq.core.factory;

import com.qualityplus.fastmessaging.api.connection.FastMQConnection;
import com.qualityplus.fastmessaging.api.credentials.Credentials;
import com.qualityplus.fastmessaging.api.factory.ConnectionFactory;
import com.qualityplus.fastmq.base.connection.FastRabbitMQConnection;
import com.qualityplus.fastmq.base.connection.FastRedisConnection;

public final class FastMQConnectionFactory implements ConnectionFactory {
    @Override
    public FastMQConnection getConnection(final Credentials credentials) {
        switch (credentials.getType()) {
            case RABBIT_MQ:
                return FastRabbitMQConnection.of(credentials);
            case REDIS:
                return FastRedisConnection.of(credentials);
            default:
                return null;
        }
    }
}
