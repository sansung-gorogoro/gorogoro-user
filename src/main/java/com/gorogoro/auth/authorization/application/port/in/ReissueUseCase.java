package com.gorogoro.auth.authorization.application.port.in;

import com.gorogoro.auth.authorization.application.dto.result.ReissueResult;

public interface ReissueUseCase {
    ReissueResult reissue(String refreshToken);
}
