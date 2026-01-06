package com.gorogoro.auth.user.application.port.in;

import com.gorogoro.auth.user.application.dto.result.UserResult;

public interface GetUserUseCase {
    UserResult getUser(Long userId);
}
