package com.gorogoro.auth.user.adapter.in.server.response;

import com.gorogoro.auth.user.application.dto.result.UserResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNicknameResponse {
    private Long userId;
    private String nickname;

    public static UserNicknameResponse from(UserResult result) {
        return UserNicknameResponse.builder()
                .userId(result.getUserId())
                .nickname(result.getName())
                .build();
    }
}
