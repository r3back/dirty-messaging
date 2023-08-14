package com.qualityplus.dirtymessaging.api.credentials;

public interface Credentials {
    public String getUri();
    public String getPrefix();
    public MessagingType getType();

    public enum MessagingType {
        RABBIT_MQ,
        REDIS
    }
}
