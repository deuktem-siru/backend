package com.deuktemsiru.adapter.inbound.web.seller

import com.deuktemsiru.application.dto.BusinessInfoResponse
import com.deuktemsiru.application.dto.BusinessInfoRequest
import com.deuktemsiru.common.ApiResponse
import com.deuktemsiru.common.created
import com.deuktemsiru.adapter.out.security.CurrentMemberId
import com.deuktemsiru.application.service.SellerAppService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Seller Auth", description = "판매자 사업자 정보 API")
@RestController
@RequestMapping("/api/v1/sellers")
class SellerAuthController(
    private val sellerAppService: SellerAppService,
) {
    /**
     * POST /api/v1/sellers/business-info
     * 사업자 정보 등록
     */
    @PostMapping("/business-info")
    fun registerBusinessInfo(
        @CurrentMemberId sellerId: Long,
        @RequestBody req: BusinessInfoRequest,
    ): ResponseEntity<ApiResponse<BusinessInfoResponse>> {
        val info = sellerAppService.registerBusinessInfo(sellerId, req.businessNumber, req.businessName)
        return created(BusinessInfoResponse.from(info), "사업자 정보 등록 성공")
    }
}
