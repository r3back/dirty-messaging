package com.qualityplus.fastmq.core.message;

import com.qualityplus.fastmessaging.api.serialization.annotation.AnnotationMessageSerializer;
import com.qualityplus.fastmessaging.api.serialization.annotation.FastMQField;
import lombok.Builder;
import lombok.Getter;

/**
 * Example MQ Message
 */
@Getter
@Builder
public final class FastMQMessage implements AnnotationMessageSerializer {
    @FastMQField
    private final Integer someInt;
    @FastMQField
    private final String someString;
    @FastMQField
    private final byte[] someBytes;

    public FastMQMessage(final int someInt, final String someString, final byte[] someBytes) {
        this.someInt = someInt;
        this.someString = someString;
        this.someBytes = someBytes;
    }
}