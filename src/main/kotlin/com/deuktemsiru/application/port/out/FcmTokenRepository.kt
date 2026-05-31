package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.FcmToken
import com.deuktemsiru.domain.Member
import java.util.Optional

interface FcmTokenRepository : EntityStore<FcmToken, Long> {
    fun findByToken(token: String): Optional<FcmToken>
    fun findByMemberAndIsActiveTrue(member: Member): List<FcmToken>

    /** 특정 회원의 모든 FCM Token 비활성화 (로그아웃) */
    
    
    fun deactivateAllByMember(member: Member)
}
