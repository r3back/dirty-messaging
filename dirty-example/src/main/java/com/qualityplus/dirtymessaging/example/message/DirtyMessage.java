package com.qualityplus.dirtymessaging.example.message;

import com.qualityplus.dirtymessaging.api.serialization.annotation.AnnotationMessageSerializer;
import com.qualityplus.dirtymessaging.api.serialization.annotation.DirtyField;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class DirtyMessage implements AnnotationMessageSerializer {
    @DirtyField
    private final Integer someInt;
    @DirtyField
    private final String someString;
    @DirtyField
    private final byte[] someBytes;

    public DirtyMessage(int someInt, String someString, byte[] someBytes) {
        this.someInt = someInt;
        this.someString = someString;
        this.someBytes = someBytes;
    }
}