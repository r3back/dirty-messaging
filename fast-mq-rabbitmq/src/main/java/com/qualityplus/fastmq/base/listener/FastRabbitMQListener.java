package com.qualityplus.fastmq.base.listener;

import com.qualityplus.fastmessaging.FastMQListener;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public final class FastRabbitMQListener extends FastMQListener implements DeliverCallback {
    public FastRabbitMQListener(final String prefix) {
        super(prefix);
    }

    @Override
    public void handle(final String consumerTag, final Delivery message) {
        super.handle(consumerTag, message.getProperties().getType(), message.getBody());
    }
}