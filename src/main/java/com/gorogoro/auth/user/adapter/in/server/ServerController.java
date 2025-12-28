package com.gorogoro.auth.user.adapter.in.server;

import com.gorogoro.auth.user.adapter.in.server.response.UserNicknameResponse;
import com.gorogoro.auth.user.application.port.in.GetUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server/users")
@RequiredArgsConstructor
public class ServerController {
    private final GetUserUseCase getUserUseCase;

    @GetMapping("/nickname")
    public ResponseEntity<UserNicknameResponse> getUser(@RequestParam Long userId) {
        return ResponseEntity.ok(UserNicknameResponse.from(getUserUseCase.getUser(userId)));
    }
}

