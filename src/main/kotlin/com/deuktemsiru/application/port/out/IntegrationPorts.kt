package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Member
import com.deuktemsiru.application.service.KakaoUserInfo
import java.io.InputStream

interface IdentityProvider { fun getUserInfo(kakaoAccessToken: String): KakaoUserInfo }
interface TokenIssuer {
    val refreshTokenExpirationSeconds: Long
    fun createAccessToken(member: Member): String
    fun createRefreshToken(member: Member): String
    fun validateRefreshToken(token: String): Long?
}
interface UploadImage {
    val isEmpty: Boolean
    val contentType: String?
    val originalFilename: String?
    val inputStream: InputStream
}
interface ImageStorage { fun save(image: UploadImage?): String? }
interface PushSender {
    val available: Boolean
    fun send(tokens: List<String>, title: String, body: String): Int
}
