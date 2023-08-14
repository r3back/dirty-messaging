package com.qualityplus.dirtymessaging;

import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.api.serialization.MessagePackSerializable;

/**
 * Client interface
 */
public interface DirtyClient {
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
     * @param subscriber {@link DirtySubscriber} handler for message class
     * @param <T>        Generic type that extends from {@link MessagePackSerializable}
     */
    public <T extends MessagePackSerializable> void addSubscriber(final Class<T> clazz,
                                                                  final DirtySubscriber<T> subscriber);
}
