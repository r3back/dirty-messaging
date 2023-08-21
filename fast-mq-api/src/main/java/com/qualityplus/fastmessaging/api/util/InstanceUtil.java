package com.qualityplus.fastmessaging.api.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class InstanceUtil {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object allocateInstance(Class<?> var1) throws InstantiationException {
        return unsafe.allocateInstance(var1);
    }
}
