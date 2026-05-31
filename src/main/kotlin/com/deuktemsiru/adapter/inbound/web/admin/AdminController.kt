package com.deuktemsiru.adapter.inbound.web.admin

import com.deuktemsiru.common.ApiResponse
import com.deuktemsiru.common.ok
import com.deuktemsiru.common.toEnumOrThrow
import com.deuktemsiru.application.dto.BusinessVerificationResponse
import com.deuktemsiru.application.dto.SettlementItem
import com.deuktemsiru.application.dto.SettlementStatusRequest
import com.deuktemsiru.application.dto.StoreVerificationResponse
import com.deuktemsiru.application.dto.VerificationRequest
import com.deuktemsiru.domain.SettlementStatus
import com.deuktemsiru.adapter.out.security.ADMIN_TOKEN_HEADER
import com.deuktemsiru.adapter.out.security.AdminAccess
import com.deuktemsiru.application.service.AdminService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * 운영자 전용 승인·정산 API. (PLAN P1 승인·정산)
 * JWT가 아니라 `X-Admin-Token` 헤더로 인증한다. `app.admin.token`(APP_ADMIN_TOKEN)이 비어 있으면 모두 401이다.
 */
@Tag(name = "Admin", description = "운영자 전용 승인·정산 API (X-Admin-Token 헤더 필요)")
@RestController
@RequestMapping("/api/v1/admin")
class AdminController(
    private val adminService: AdminService,
    private val adminAccess: AdminAccess,
) {

    /** POST /api/v1/admin/business-infos/{businessInfoId}/verification — 사업자 승인/반려 */
    @PostMapping("/business-infos/{businessInfoId}/verification")
    fun verifyBusiness(
        @RequestHeader(name = ADMIN_TOKEN_HEADER, required = false) token: String?,
        @PathVariable businessInfoId: Long,
        @RequestBody req: VerificationRequest,
    ): ApiResponse<BusinessVerificationResponse> {
        adminAccess.requireAdmin(token)
        return ok(
            adminService.verifyBusiness(businessInfoId, req.approved, req.siruApproved),
            if (req.approved) "사업자 정보를 승인했습니다." else "사업자 정보를 반려했습니다.",
        )
    }

    /** POST /api/v1/admin/stores/{storeId}/verification — 매장 승인/반려 */
    @PostMapping("/stores/{storeId}/verification")
    fun verifyStore(
        @RequestHeader(name = ADMIN_TOKEN_HEADER, required = false) token: String?,
        @PathVariable storeId: Long,
        @RequestBody req: VerificationRequest,
    ): ApiResponse<StoreVerificationResponse> {
        adminAccess.requireAdmin(token)
        return ok(
            adminService.verifyStore(storeId, req.approved),
            if (req.approved) "매장을 승인했습니다." else "매장 승인을 해제했습니다.",
        )
    }

    /** POST /api/v1/admin/settlements/{settlementId}/status — 정산 지급/반려 */
    @PostMapping("/settlements/{settlementId}/status")
    fun updateSettlementStatus(
        @RequestHeader(name = ADMIN_TOKEN_HEADER, required = false) token: String?,
        @PathVariable settlementId: Long,
        @RequestBody req: SettlementStatusRequest,
    ): ApiResponse<SettlementItem> {
        adminAccess.requireAdmin(token)
        val status = req.status.toEnumOrThrow<SettlementStatus>("정산 상태")
        return ok(
            adminService.updateSettlementStatus(settlementId, status),
            if (status == SettlementStatus.COMPLETED) "정산을 지급 완료 처리했습니다." else "정산을 반려했습니다.",
        )
    }

}
