package com.qualityplus.dirtymessaging.base.connection;

import com.qualityplus.dirtymessaging.api.connection.DirtyConnection;
import com.qualityplus.dirtymessaging.api.credentials.DirtyCredentials;
import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.api.handler.MessageHandlerRegistry;
import com.qualityplus.dirtymessaging.api.publisher.DirtyPublisher;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;
import com.qualityplus.dirtymessaging.base.listener.RabbitMQMessagePubSubListener;
import com.qualityplus.dirtymessaging.base.publisher.RabbitMQPublisher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public final class RabbitMQMessagingConnection implements DirtyConnection {
    private final MessageHandlerRegistry messageHandlerRegistry = MessageHandlerRegistry.INSTANCE;
    private final DirtyPublisher publisher;

    public static DirtyConnection of(DirtyCredentials credentials){
        return new RabbitMQMessagingConnection(credentials);
    }

    private RabbitMQMessagingConnection(DirtyCredentials credentials) {
        this.publisher = createPublisher(credentials);
    }

    @Override
    public void publish(MessagePackSerializable message) {
        publisher.publish(message);
    }

    @Override
    public <T extends MessagePackSerializable> void addHandler(Class<T> clazz, MessageHandler<T> consumer) {
        messageHandlerRegistry.addHandler(clazz, consumer);
    }

    private DirtyPublisher createPublisher(DirtyCredentials credentials){
        String uri = credentials.getUri();

        String prefix = credentials.getPrefix();

        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            connectionFactory.setUri(uri);
        }catch (Exception e){
            e.printStackTrace();
        }

        registerRabbitMQPubSubListener(connectionFactory, prefix);

        return new RabbitMQPublisher(connectionFactory, prefix);
    }


    private static void registerRabbitMQPubSubListener(ConnectionFactory factory, String prefix) {
        try {
            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();

            channel.exchangeDeclare(prefix, "fanout");

            String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, prefix, "");

            channel.basicConsume(queueName, true, new RabbitMQMessagePubSubListener(prefix), consumerTag -> { });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
