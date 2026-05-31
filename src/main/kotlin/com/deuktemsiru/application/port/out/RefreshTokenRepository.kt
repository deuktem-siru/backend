package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.RefreshToken
import java.util.Optional

interface RefreshTokenRepository : EntityStore<RefreshToken, Long> {

    /** 유효한(미폐기) Refresh Token 조회 */
    fun findByTokenAndIsRevokedFalse(token: String): Optional<RefreshToken>

    /** 특정 회원의 모든 Refresh Token 폐기 (로그아웃) */
    
    
    fun revokeAllByMember(member: Member)

    /** 만료된 단일 토큰 폐기 — REQUIRES_NEW 트랜잭션에서 호출해야 예외 롤백에 영향을 받지 않음 */
    
    
    fun revokeById( id: Long)
}
