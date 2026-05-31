package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface RefreshTokenRepository : JpaEntityStore<RefreshToken, Long>, com.deuktemsiru.application.port.out.RefreshTokenRepository {

    /** 유효한(미폐기) Refresh Token 조회 */
    override fun findByTokenAndIsRevokedFalse(token: String): Optional<RefreshToken>

    /** 특정 회원의 모든 Refresh Token 폐기 (로그아웃) */
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.member = :member")
    override fun revokeAllByMember(member: Member)

    /** 만료된 단일 토큰 폐기 — REQUIRES_NEW 트랜잭션에서 호출해야 예외 롤백에 영향을 받지 않음 */
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.refreshTokenId = :id")
    override fun revokeById(@Param("id") id: Long)
}
