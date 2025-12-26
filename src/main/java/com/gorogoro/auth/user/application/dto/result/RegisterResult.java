package com.gorogoro.auth.user.application.dto.result;

import com.gorogoro.auth.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResult {
    private Long userId;
    private String email;
    private String name;

    public static RegisterResult from(User savedUser) {
        return RegisterResult.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .build();
    }
}

