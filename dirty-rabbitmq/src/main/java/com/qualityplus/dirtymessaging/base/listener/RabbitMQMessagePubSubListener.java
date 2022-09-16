package com.qualityplus.dirtymessaging.base.listener;

import com.qualityplus.dirtymessaging.DirtyPubSub;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public final class RabbitMQMessagePubSubListener extends DirtyPubSub implements DeliverCallback {
    private final String prefix;

    public RabbitMQMessagePubSubListener(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void handle(String consumerTag, Delivery message) {
        String className = consumerTag.replace(prefix, "");

        try {
            Class<?> clazz = Class.forName(message.getProperties().getType());

            if (!MessagePackSerializable.class.isAssignableFrom(clazz)) {
                log.error("{} does not implement MessagePackSerializable", className);
                return;
            }

            MessagePackSerializable messagePackSerializable = (MessagePackSerializable) unsafe.allocateInstance(clazz);

            messagePackSerializable.deserialize(message.getBody());

            registry.callAll(clazz, messagePackSerializable);
        } catch (ClassNotFoundException | InstantiationException | IOException e) {
            log.error("error while handling message on channel {} with classname {}", "channel", className, e);
        }
    }
}