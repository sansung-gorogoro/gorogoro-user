package com.gorogoro.auth.user.application.port.in;

import com.gorogoro.auth.user.application.dto.command.RegisterCommand;
import com.gorogoro.auth.user.application.dto.result.RegisterResult;

public interface RegisterUseCase {
    RegisterResult register(RegisterCommand request);
}

