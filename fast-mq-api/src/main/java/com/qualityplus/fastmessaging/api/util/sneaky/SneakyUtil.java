package com.qualityplus.fastmessaging.api.util.sneaky;

public abstract class SneakyUtil {
    @SuppressWarnings("unchecked")
    static <E extends Exception> void sneakyThrow(Exception e) throws E {
        throw (E) e;
    }
}
