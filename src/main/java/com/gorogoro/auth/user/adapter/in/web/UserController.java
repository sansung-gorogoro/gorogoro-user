package com.gorogoro.auth.user.adapter.in.web;

import com.gorogoro.auth.user.adapter.in.web.request.RegisterRequest;
import com.gorogoro.auth.user.adapter.in.web.response.RegisterResponse;
import com.gorogoro.auth.user.application.dto.command.RegisterCommand;
import com.gorogoro.auth.user.application.port.in.RegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUseCase registerUseCase;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterCommand command = RegisterCommand.toCommand(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RegisterResponse.from(registerUseCase.register(command)));
    }
}

