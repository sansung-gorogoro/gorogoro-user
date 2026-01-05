package com.gorogoro.auth.authorization.adapter.in.web;

import com.gorogoro.auth.authorization.adapter.in.web.request.LoginRequest;
import com.gorogoro.auth.authorization.adapter.in.web.response.LoginResponse;
import com.gorogoro.auth.authorization.adapter.in.web.response.ReissueResponse;
import com.gorogoro.auth.authorization.application.dto.command.LoginCommand;
import com.gorogoro.auth.authorization.application.dto.result.LoginResult;
import com.gorogoro.auth.authorization.application.port.in.LoginUseCase;
import com.gorogoro.auth.authorization.application.port.in.ReissueUseCase;
import com.gorogoro.auth.global.jwt.JwtConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final LoginUseCase loginUseCase;
    private final ReissueUseCase reissueUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginCommand command = LoginCommand.toCommand(request);
        LoginResult result = loginUseCase.login(command);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", result.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(JwtConstants.REFRESH_TOKEN_EXPIRATION_MILLIS / 1000)
                .sameSite("None")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(LoginResponse.from(result));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@CookieValue(value = "refresh_token") String refreshToken) {
        return ResponseEntity.ok(
                ReissueResponse.from(reissueUseCase.reissue(refreshToken)));
    }
}

