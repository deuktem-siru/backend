package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.Notification

interface NotificationRepository : EntityStore<Notification, Long> {
    fun findByMemberOrderByCreatedAtDesc(member: Member): List<Notification>
    fun findByRelatedStoreIdAndTitleOrderByCreatedAtDesc(relatedStoreId: Long, title: String): List<Notification>
}
