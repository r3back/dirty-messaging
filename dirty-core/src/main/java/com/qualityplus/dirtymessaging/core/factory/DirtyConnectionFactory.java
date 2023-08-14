package com.qualityplus.dirtymessaging.core.factory;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.Credentials;
import com.qualityplus.dirtymessaging.api.factory.ConnectionFactory;
import com.qualityplus.dirtymessaging.base.connection.RabbitMQMessagingConnection;
import com.qualityplus.dirtymessaging.base.connection.RedisMessagingConnection;

public final class DirtyConnectionFactory implements ConnectionFactory {
    @Override
    public DirtyConnection getConnection(final Credentials credentials) {
        switch (credentials.getType()) {
            case RABBIT_MQ:
                return RabbitMQMessagingConnection.of(credentials);
            case REDIS:
                return RedisMessagingConnection.of(credentials);
            default:
                return null;
        }
    }
}
