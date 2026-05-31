package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.Orders
import com.deuktemsiru.domain.Review
import com.deuktemsiru.domain.Store
import com.deuktemsiru.application.port.out.PageQuery

interface ReviewRatingSummary {
    val reviewCount: Long
    val ratingAvg: Double?
}

interface ReviewRepository : EntityStore<Review, Long> {
    fun findByStoreAndIsDeletedFalseOrderByCreatedAtDesc(store: Store, pageable: PageQuery): List<Review>
    fun existsByConsumerAndOrder(consumer: Member, order: Orders): Boolean

    
    fun summarizeRatingByStore(store: Store): ReviewRatingSummary
}
