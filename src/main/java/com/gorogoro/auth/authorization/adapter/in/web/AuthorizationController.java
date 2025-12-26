package com.gorogoro.auth.authorization.adapter.in.web;

import com.gorogoro.auth.authorization.adapter.in.web.request.LoginRequest;
import com.gorogoro.auth.authorization.adapter.in.web.response.LoginResponse;
import com.gorogoro.auth.authorization.application.dto.command.LoginCommand;
import com.gorogoro.auth.authorization.application.port.in.LoginUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginCommand command = LoginCommand.toCommand(request);
        return ResponseEntity.ok(LoginResponse.from(loginUseCase.login(command)));
    }
}

