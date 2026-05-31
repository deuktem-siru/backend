package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.FcmToken
import com.deuktemsiru.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface FcmTokenRepository : JpaEntityStore<FcmToken, Long>, com.deuktemsiru.application.port.out.FcmTokenRepository {
    override fun findByToken(token: String): Optional<FcmToken>
    override fun findByMemberAndIsActiveTrue(member: Member): List<FcmToken>

    /** 특정 회원의 모든 FCM Token 비활성화 (로그아웃) */
    @Modifying
    @Query("UPDATE FcmToken ft SET ft.isActive = false WHERE ft.member = :member")
    override fun deactivateAllByMember(member: Member)
}
