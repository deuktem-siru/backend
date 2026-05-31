package com.deuktemsiru.adapter.out.security

import com.deuktemsiru.domain.MemberRole

data class JwtUser(
    val memberId: Long,
    val role: MemberRole,
)
