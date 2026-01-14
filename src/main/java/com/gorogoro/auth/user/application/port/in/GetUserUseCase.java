package com.gorogoro.auth.user.application.port.in;

import com.gorogoro.auth.user.application.dto.result.UserResult;

import java.util.List;
import java.util.Map;

public interface GetUserUseCase {
    UserResult getUser(Long userId);
    Map<Long, String> getUserNicknames(List<Long> userIds);
}
