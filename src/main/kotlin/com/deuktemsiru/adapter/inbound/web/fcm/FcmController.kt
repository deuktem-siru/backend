package com.deuktemsiru.adapter.inbound.web.fcm

import com.deuktemsiru.application.dto.FcmTokenRequest
import com.deuktemsiru.common.ApiResponse
import com.deuktemsiru.common.ok
import com.deuktemsiru.adapter.out.security.CurrentMemberId
import com.deuktemsiru.application.service.FcmService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "FCM", description = "FCM 토큰 등록 API")
@RestController
@RequestMapping("/api/v1/fcm")
class FcmController(
    private val fcmService: FcmService,
) {

    /**
     * POST /api/v1/fcm/token
     * FCM 디바이스 토큰 등록 / 갱신
     */
    @PostMapping("/token")
    fun registerFcmToken(
        @CurrentMemberId memberId: Long,
        @RequestBody req: FcmTokenRequest,
    ): ApiResponse<Unit> {
        fcmService.registerToken(memberId, req.token, req.deviceInfo)
        return ok(Unit, "FCM 토큰이 등록되었습니다.")
    }
}
