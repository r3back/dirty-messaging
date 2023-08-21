package com.qualityplus.fastmessaging;

import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmessaging.api.serialization.MessagePackSerializable;

/**
 * MQ Client interface
 */
public interface FastMQClient {
    /**
     * Method to Publish messages to all
     * client subscribers.
     *
     * @param message {@link MessagePackSerializable}
     */
    public void publish(final MessagePackSerializable message);

    /**
     * Add subscriber to handle specific message
     * classes.
     *
     * @param clazz      Message Class
     * @param subscriber {@link FastMQSubscriber} handler for message class
     * @param <T>        Generic type that extends from {@link MessagePackSerializable}
     */
    public <T extends MessagePackSerializable> void addSubscriber(final Class<T> clazz,
                                                                  final FastMQSubscriber<T> subscriber);
}
