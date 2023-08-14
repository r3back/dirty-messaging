package com.qualityplus.dirtymessaging.base.listener;

import com.qualityplus.dirtymessaging.DirtyListener;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public final class DirtyRabbitMQListener extends DirtyListener implements DeliverCallback {
    public DirtyRabbitMQListener(final String prefix) {
        super(prefix);
    }

    @Override
    public void handle(final String consumerTag, final Delivery message) {
        super.handle(consumerTag, message.getProperties().getType(), message.getBody());
    }
}