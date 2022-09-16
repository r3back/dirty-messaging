package com.qualityplus.dirtymessaging.base.connection.factory;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.DirtyCredentials;
import com.qualityplus.dirtymessaging.base.connection.RedisMessagingConnection;

public final class RedisMessagingConnectionFactory {
    public static DirtyConnection getConnection(DirtyCredentials credentials) {
        return RedisMessagingConnection.of(credentials);
    }
}
