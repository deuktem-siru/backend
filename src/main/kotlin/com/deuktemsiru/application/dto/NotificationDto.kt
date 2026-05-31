package com.deuktemsiru.application.dto

import com.deuktemsiru.domain.Notification
import com.deuktemsiru.domain.NotificationType
import java.time.LocalDateTime

data class NotificationResponse(
    val notificationId: Long,
    val type: NotificationType,
    val title: String,
    val body: String,
    val isRead: Boolean,
    val relatedStoreId: Long?,
    val relatedOrderId: Long?,
    val relatedProductId: Long?,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(notification: Notification) = NotificationResponse(
            notificationId = notification.notificationId,
            type = notification.type,
            title = notification.title,
            body = notification.body,
            isRead = notification.isRead,
            relatedStoreId = notification.relatedStoreId,
            relatedOrderId = notification.relatedOrderId,
            relatedProductId = notification.relatedProductId,
            createdAt = notification.createdAt,
        )
    }
}
