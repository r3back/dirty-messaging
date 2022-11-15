package com.qualityplus.dirtymessaging.core.credentials;

import com.qualityplus.dirtymessaging.api.credentials.DirtyCredentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public final class MessagingCredentials implements DirtyCredentials {
    private String id;
    private String uri;
    private String prefix;
    private MessagingType type;
}
