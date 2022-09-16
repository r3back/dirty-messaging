package com.qualityplus.dirtymessaging.api.credentials;

public interface DirtyCredentials {
    String getUri();
    String getPrefix();
    MessagingType getType();

    public enum MessagingType {
        RABBIT_MQ,
        REDIS
    }
}
