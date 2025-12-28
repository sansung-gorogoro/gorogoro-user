package com.gorogoro.auth.user.application.port.in;

import com.gorogoro.auth.user.application.dto.command.UpdateUserCommand;
import com.gorogoro.auth.user.application.dto.result.UserResult;

public interface UpdateUserUseCase {
    UserResult updateUser(UpdateUserCommand command);
}
