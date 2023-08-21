package com.qualityplus.fastmq.base.publisher;

import com.qualityplus.fastmessaging.api.publisher.FastMQPublisher;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FastRabbitMQPublisher implements FastMQPublisher {
    private final ConnectionFactory connectionFactory;
    private final String prefix;

    public FastRabbitMQPublisher(ConnectionFactory connectionFactory, String prefix) {
        this.connectionFactory = connectionFactory;
        this.prefix = prefix;
    }

    @Override
    public void publish(MessagePackSerializable message) {
        String className = message.getClass().getName();

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(prefix, "fanout");

            AMQP.BasicProperties properties = new AMQP.BasicProperties()
                    .builder()
                    .type(className)
                    .build();

            channel.basicPublish(prefix, "", properties, message.serialize());

        }catch (Exception e) {
            log.error("error while publishing message {}", className, e);
        }
    }

}