package com.qualityplus.dirtymessaging.example;

import com.qualityplus.dirtymessaging.DirtyService;
import com.qualityplus.dirtymessaging.api.credentials.DirtyCredentials.MessagingType;
import com.qualityplus.dirtymessaging.base.service.factory.RedisMessagingServiceFactory;
import com.qualityplus.dirtymessaging.core.credentials.MessagingCredentials;
import com.qualityplus.dirtymessaging.example.handler.DirtyMessageHandler;
import com.qualityplus.dirtymessaging.example.message.DirtyMessage;

public final class DirtyExample {
    private static final String REDIS_URI = "redis://user:password@host:port";
    private static final String PREFIX = "REDIS_PREFIX";

    public void dirtyExampleMethod(){
        MessagingCredentials credentials = MessagingCredentials.builder()
                .uri(REDIS_URI)
                .prefix(PREFIX)
                .type(MessagingType.REDIS)
                .build();

        DirtyService service = RedisMessagingServiceFactory
                .withCredentials(credentials)
                .withHandler(DirtyMessage.class, new DirtyMessageHandler())
                .create();

        DirtyMessage message = DirtyMessage.builder()
                .someInt(1)
                .someString("someString")
                .someBytes(new byte[]{1, 2, 3})
                .build();

        service.publish(message);
    }
}
