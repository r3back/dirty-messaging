package com.qualityplus.fastmessaging.api.factory;

import com.qualityplus.fastmessaging.api.connection.FastMQConnection;
import com.qualityplus.fastmessaging.api.credentials.Credentials;

public interface ConnectionFactory {
    public FastMQConnection getConnection(final Credentials credentials);
}
