package com.gorogoro.auth.user.adapter.in.web;

import com.gorogoro.auth.user.adapter.in.web.request.RegisterUserRequest;
import com.gorogoro.auth.user.adapter.in.web.request.UpdateUserRequest;
import com.gorogoro.auth.user.adapter.in.web.response.UserResponse;
import com.gorogoro.auth.user.application.dto.command.RegisterUserCommand;
import com.gorogoro.auth.user.application.dto.command.UpdateUserCommand;
import com.gorogoro.auth.user.application.port.in.GetUserUseCase;
import com.gorogoro.auth.user.application.port.in.RegisterUserUseCase;
import com.gorogoro.auth.user.application.port.in.UpdateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = RegisterUserCommand.toCommand(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.from(registerUserUseCase.register(command)));
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                UserResponse.from(getUserUseCase.getUser(Long.valueOf(userDetails.getUsername())))
        );
    }

    @PatchMapping("/update")
    public ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        UpdateUserCommand command = UpdateUserCommand.of(Long.valueOf(userDetails.getUsername()), request);
        return ResponseEntity.ok(
                UserResponse.from(updateUserUseCase.updateUser(command))
        );
    }
}

