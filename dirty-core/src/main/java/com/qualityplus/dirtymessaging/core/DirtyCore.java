package com.qualityplus.dirtymessaging.core;

import com.qualityplus.dirtymessaging.DirtyClient;
import com.qualityplus.dirtymessaging.api.credentials.Credentials;
import com.qualityplus.dirtymessaging.core.builder.DirtyClientBuilder;
import com.qualityplus.dirtymessaging.core.credentials.DirtyCredentials;
import com.qualityplus.dirtymessaging.core.sub.PrintSubscriber;
import com.qualityplus.dirtymessaging.core.message.DirtyMessage;

public final class DirtyCore {
    private static final String REDIS_URI = "redis://user:password@host:port";
    private static final String PREFIX = "REDIS_PREFIX";

    private void clientCreationExample(){
        final DirtyCredentials credentials = DirtyCredentials.builder()
                .uri(REDIS_URI)
                .prefix(PREFIX)
                .type(Credentials.MessagingType.REDIS)
                .build();

        final DirtyClient client = new DirtyClientBuilder()
                .withCredentials(credentials)
                .withSubscriber(DirtyMessage.class, new PrintSubscriber())
                .create();

        final DirtyMessage message = DirtyMessage.builder()
                .someInt(1)
                .someString("someString")
                .someBytes(new byte[]{1, 2, 3})
                .build();

        client.publish(message);
    }
}
