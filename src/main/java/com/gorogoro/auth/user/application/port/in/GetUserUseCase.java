package com.gorogoro.auth.user.application.port.in;

import com.gorogoro.auth.user.application.dto.result.UserNicknamesResult;
import com.gorogoro.auth.user.application.dto.result.UserResult;

import java.util.List;

public interface GetUserUseCase {
    UserResult getUser(Long userId);
    UserNicknamesResult getUserNicknames(List<Long> userIds);
}
