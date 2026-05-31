package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaEntityStore<Notification, Long>, com.deuktemsiru.application.port.out.NotificationRepository {
    override fun findByMemberOrderByCreatedAtDesc(member: Member): List<Notification>
    override fun findByRelatedStoreIdAndTitleOrderByCreatedAtDesc(relatedStoreId: Long, title: String): List<Notification>
}
