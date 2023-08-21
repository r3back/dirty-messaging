package com.qualityplus.fastmq.core.credentials;

import com.qualityplus.fastmessaging.api.credentials.Credentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public final class FastMQCredentials implements Credentials {
    private String id;
    private String uri;
    private String prefix;
    private MessagingType type;
}
