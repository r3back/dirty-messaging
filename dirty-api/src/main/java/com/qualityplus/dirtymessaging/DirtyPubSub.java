package com.qualityplus.dirtymessaging;

import com.qualityplus.dirtymessaging.api.handler.MessageHandlerRegistry;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public abstract class DirtyPubSub {
    protected final MessageHandlerRegistry registry = MessageHandlerRegistry.INSTANCE;
    protected static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
