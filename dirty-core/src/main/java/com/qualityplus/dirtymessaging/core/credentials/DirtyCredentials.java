package com.qualityplus.dirtymessaging.core.credentials;

import com.qualityplus.dirtymessaging.api.credentials.Credentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public final class DirtyCredentials implements Credentials {
    private String id;
    private String uri;
    private String prefix;
    private MessagingType type;
}
