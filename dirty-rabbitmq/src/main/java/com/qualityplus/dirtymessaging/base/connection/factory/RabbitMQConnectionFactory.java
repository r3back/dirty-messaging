package com.qualityplus.dirtymessaging.base.connection.factory;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.DirtyCredentials;
import com.qualityplus.dirtymessaging.base.connection.RabbitMQMessagingConnection;

public final class RabbitMQConnectionFactory {
    public static DirtyConnection getConnection(DirtyCredentials credentials) {
        return RabbitMQMessagingConnection.of(credentials);
    }
}
