package com.qualityplus.fastmq.core.sub;

import com.qualityplus.fastmessaging.api.sub.FastMQSubscriber;
import com.qualityplus.fastmq.core.message.FastMQMessage;

/**
 * Example subscriber that print the string field from received message
 */
public final class PrintSubscriber implements FastMQSubscriber<FastMQMessage> {
    /**
     * Handles a message when is received
     *
     * @param message {@link FastMQMessage} received message
     */
    @Override
    public void accept(final FastMQMessage message) {
        System.out.println(message.getSomeString());
    }

    /**
     * Retrieves If the subscriber work only
     * one time
     *
     * @return true if it's one time message
     */
    @Override
    public boolean isOneTime() {
        return false;
    }
}