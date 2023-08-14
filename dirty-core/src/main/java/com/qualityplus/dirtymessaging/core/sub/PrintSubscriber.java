package com.qualityplus.dirtymessaging.core.sub;

import com.qualityplus.dirtymessaging.api.sub.DirtySubscriber;
import com.qualityplus.dirtymessaging.core.message.DirtyMessage;

/**
 * Example subscriber that print the string field from received message
 */
public final class PrintSubscriber implements DirtySubscriber<DirtyMessage> {
    /**
     * Handles a message when is received
     *
     * @param message {@link DirtyMessage} received message
     */
    @Override
    public void accept(final DirtyMessage message) {
        System.out.println(message.getSomeString());
    }

    @Override
    public boolean isOneTime() {
        return false;
    }
}