package com.qualityplus.dirtymessaging.api.util.sneaky;

import java.util.function.BiConsumer;

public interface SneakyBiConsumer<T, U, E extends Exception> {
    public static <T, U> BiConsumer<T, U> of(final SneakyBiConsumer<? super T, ? super U, ? extends Exception> cons) {
        return (e, f) -> {
            try {
                cons.accept(e, f);
            } catch (Exception ex) {
                SneakyUtil.sneakyThrow(ex);
            }
        };
    }

    public void accept(final T t, final U u) throws E;
}
