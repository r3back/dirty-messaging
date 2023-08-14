package com.qualityplus.dirtymessaging.api.factory;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.Credentials;

public interface ConnectionFactory {
    public DirtyConnection getConnection(final Credentials credentials);
}
