package com.gorogoro.auth.authorization.application.dto.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReissueResult {
    private String accessToken;

    public static ReissueResult of(String accessToken) {
        return ReissueResult.builder()
                .accessToken(accessToken)
                .build();
    }
}
