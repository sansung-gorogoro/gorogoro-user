package com.gorogoro.auth.user.application.port.out;

import com.gorogoro.auth.user.domain.model.User;

public interface UserCommandPort {
    User saveUser(User user);
}
