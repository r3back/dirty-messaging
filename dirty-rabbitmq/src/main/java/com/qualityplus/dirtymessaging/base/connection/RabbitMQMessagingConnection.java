package com.qualityplus.dirtymessaging.base.connection;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.Credentials;
import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.api.sub.MessageHandlerRegistry;
import com.qualityplus.dirtymessaging.api.publisher.DirtyPublisher;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.base.listener.DirtyRabbitMQListener;
import com.qualityplus.dirtymessaging.base.publisher.DirtyRabbitMQPublisher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public final class RabbitMQMessagingConnection implements DirtyConnection {
    private final MessageHandlerRegistry messageHandlerRegistry = MessageHandlerRegistry.INSTANCE;
    private final DirtyPublisher publisher;

    public static DirtyConnection of(final Credentials credentials){
        return new RabbitMQMessagingConnection(credentials);
    }

    private RabbitMQMessagingConnection(final Credentials credentials) {
        this.publisher = createPublisher(credentials);
    }

    @Override
    public void publish(final MessagePackSerializable message) {
        this.publisher.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(final Class<T> clazz, final DirtySubscriber<T> consumer) {
        this.messageHandlerRegistry.addHandler(clazz, consumer);
    }

    private DirtyPublisher createPublisher(final Credentials credentials){
        final String uri = credentials.getUri();

        final String prefix = credentials.getPrefix();

        final ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            connectionFactory.setUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerRabbitMQPubSubListener(connectionFactory, prefix);

        return new DirtyRabbitMQPublisher(connectionFactory, prefix);
    }


    private static void registerRabbitMQPubSubListener(final ConnectionFactory factory, final String prefix) {
        try {
            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();

            channel.exchangeDeclare(prefix, "fanout");

            String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, prefix, "");

            channel.basicConsume(queueName, true, new DirtyRabbitMQListener(prefix), consumerTag -> { });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
