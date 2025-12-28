package com.gorogoro.auth.user.application.port.in;

import com.gorogoro.auth.user.application.dto.command.RegisterUserCommand;
import com.gorogoro.auth.user.application.dto.result.UserResult;

public interface RegisterUserUseCase {
    UserResult register(RegisterUserCommand request);
}

