package com.qualityplus.fastmq.base.connection;

import com.qualityplus.fastmessaging.api.connection.FastMQConnection;
import com.qualityplus.fastmessaging.api.credentials.Credentials;
import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmessaging.api.sub.MessageHandlerRegistry;
import com.qualityplus.fastmessaging.api.publisher.FastMQPublisher;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.fastmq.base.listener.FastRabbitMQListener;
import com.qualityplus.fastmq.base.publisher.FastRabbitMQPublisher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public final class FastRabbitMQConnection implements FastMQConnection {
    private final MessageHandlerRegistry messageHandlerRegistry = MessageHandlerRegistry.INSTANCE;
    private final FastMQPublisher publisher;

    public static FastMQConnection of(final Credentials credentials){
        return new FastRabbitMQConnection(credentials);
    }

    private FastRabbitMQConnection(final Credentials credentials) {
        this.publisher = createPublisher(credentials);
    }

    @Override
    public void publish(final MessagePackSerializable message) {
        this.publisher.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(final Class<T> clazz, final FastMQSubscriber<T> consumer) {
        this.messageHandlerRegistry.addHandler(clazz, consumer);
    }

    private FastMQPublisher createPublisher(final Credentials credentials){
        final String uri = credentials.getUri();

        final String prefix = credentials.getPrefix();

        final ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            connectionFactory.setUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerRabbitMQPubSubListener(connectionFactory, prefix);

        return new FastRabbitMQPublisher(connectionFactory, prefix);
    }


    private static void registerRabbitMQPubSubListener(final ConnectionFactory factory, final String prefix) {
        try {
            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();

            channel.exchangeDeclare(prefix, "fanout");

            String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, prefix, "");

            channel.basicConsume(queueName, true, new FastRabbitMQListener(prefix), consumerTag -> { });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
