package com.gorogoro.auth.user.application.dto.result;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class UserNicknamesResult {
    private Map<Long, String> nicknames;
    private List<Long> missingUserIds;

    public static UserNicknamesResult of(Map<Long, String> nicknames, List<Long> missingUserIds) {
        return UserNicknamesResult.builder()
                .nicknames(nicknames)
                .missingUserIds(missingUserIds)
                .build();
    }
}
