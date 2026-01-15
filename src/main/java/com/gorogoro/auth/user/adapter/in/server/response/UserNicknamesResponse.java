package com.gorogoro.auth.user.adapter.in.server.response;

import com.gorogoro.auth.user.application.dto.result.UserNicknamesResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNicknamesResponse {
    private Map<Long, String> nicknames;
    private List<Long> missingUserIds;

    public static UserNicknamesResponse from(UserNicknamesResult result) {
        return UserNicknamesResponse.builder()
                .nicknames(result.getNicknames())
                .missingUserIds(result.getMissingUserIds())
                .build();
    }
}
