package com.qualityplus.dirtymessaging.base.listener;

import com.qualityplus.dirtymessaging.DirtyListener;
import com.qualityplus.dirtymessaging.base.util.StringByteCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

public final class DirtyRedisSubListener extends DirtyListener implements RedisPubSubListener<String, byte[]> {
    public DirtyRedisSubListener(final String prefix) {
        super(prefix);
    }

    @Override
    public void message(final String channel, final byte[] message) {
    }

    @Override
    public void message(final String pattern, final String channel, final byte[] message) {
        final String className = channel.replace(prefix, "");

        super.handle(channel, className, message);

    }

    @Override
    public void subscribed(final String channel, final long count) {
    }

    @Override
    public void psubscribed(final String pattern, final long count) {
    }

    @Override
    public void unsubscribed(final String channel, final long count) {
    }

    @Override
    public void punsubscribed(final String pattern, final long count) {
    }

    public void register(final RedisClient client) {
        final StatefulRedisPubSubConnection<String, byte[]> conn = client.connectPubSub(StringByteCodec.INSTANCE);

        conn.addListener(this);

        conn.sync().psubscribe(this.prefix + "*");
    }
}