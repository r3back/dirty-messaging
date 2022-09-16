package com.qualityplus.dirtymessaging.example.handler;

import com.qualityplus.dirtymessaging.api.handler.MessageHandler;
import com.qualityplus.dirtymessaging.example.message.DirtyMessage;

public final class DirtyMessageHandler implements MessageHandler<DirtyMessage> {
    @Override
    public void accept(DirtyMessage message) {
        /**
         * Do Whatever with received message
         */
    }

    @Override
    public boolean isOneTime() {
        return false;
    }
}