package com.gorogoro.auth.authorization.application.port.in;

import com.gorogoro.auth.authorization.application.dto.command.LoginCommand;
import com.gorogoro.auth.authorization.application.dto.result.LoginResult;

public interface LoginUseCase {
    LoginResult login(LoginCommand loginCommand);
}

