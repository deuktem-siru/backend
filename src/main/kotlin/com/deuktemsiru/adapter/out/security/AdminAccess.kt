package com.deuktemsiru.adapter.out.security

import com.deuktemsiru.common.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.MessageDigest

const val ADMIN_TOKEN_HEADER = "X-Admin-Token"

/** 설정된 운영자 토큰이 32자 미만이면 운영자 API 자체가 비활성이다. */
fun adminTokenMatches(configured: String, provided: String?): Boolean {
    if (configured.length < 32 || provided == null) return false
    return MessageDigest.isEqual(provided.toByteArray(), configured.toByteArray())
}

@Component
class AdminAccess(
    @Value("\${app.admin.token:}") private val adminToken: String,
) {
    fun requireAdmin(token: String?) {
        if (!adminTokenMatches(adminToken, token)) {
            throw UnauthorizedException("운영자 토큰이 올바르지 않거나 운영자 API가 비활성화되어 있습니다.")
        }
    }
}
