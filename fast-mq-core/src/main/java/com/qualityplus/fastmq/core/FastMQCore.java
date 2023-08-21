package com.qualityplus.fastmq.core;

import com.qualityplus.fastmessaging.FastMQClient;
import com.qualityplus.fastmessaging.api.credentials.Credentials;
import com.qualityplus.fastmq.core.builder.FastMQClientBuilder;
import com.qualityplus.fastmq.core.credentials.FastMQCredentials;
import com.qualityplus.fastmq.core.sub.PrintSubscriber;
import com.qualityplus.fastmq.core.message.FastMQMessage;

public final class FastMQCore {
    private static final String REDIS_URI = "redis://user:password@host:port";
    private static final String PREFIX = "REDIS_PREFIX";

    private void clientCreationExample(){
        final FastMQCredentials credentials = FastMQCredentials.builder()
                .uri(REDIS_URI)
                .prefix(PREFIX)
                .type(Credentials.MessagingType.REDIS)
                .build();

        final FastMQClient client = new FastMQClientBuilder()
                .withCredentials(credentials)
                .withSubscriber(FastMQMessage.class, new PrintSubscriber())
                .create();

        final FastMQMessage message = FastMQMessage.builder()
                .someInt(1)
                .someString("someString")
                .someBytes(new byte[]{1, 2, 3})
                .build();

        client.publish(message);
    }
}
