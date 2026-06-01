package com.deuktemsiru.adapter.inbound.web

import com.deuktemsiru.application.port.out.UploadImage
import org.springframework.web.multipart.MultipartFile

fun MultipartFile.asUploadImage(): UploadImage = object : UploadImage {
    override val isEmpty get() = this@asUploadImage.isEmpty
    override val contentType get() = this@asUploadImage.contentType
    override val originalFilename get() = this@asUploadImage.originalFilename
    override val inputStream get() = this@asUploadImage.inputStream
}
