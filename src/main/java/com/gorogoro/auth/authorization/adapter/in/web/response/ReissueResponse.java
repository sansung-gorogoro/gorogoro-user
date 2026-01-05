package com.gorogoro.auth.authorization.adapter.in.web.response;

import com.gorogoro.auth.authorization.application.dto.result.ReissueResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReissueResponse {
    private String accessToken;

    public static ReissueResponse from(ReissueResult result) {
        return ReissueResponse.builder()
                .accessToken(result.getAccessToken())
                .build();
    }
}
