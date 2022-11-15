package com.qualityplus.dirtymessaging.api.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sub {
    boolean isOneTime() default false;

    int priority() default 0;
    String credentials();
    String channel();
}
