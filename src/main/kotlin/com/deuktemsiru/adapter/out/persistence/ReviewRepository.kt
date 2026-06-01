package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.application.port.out.ReviewRatingSummary
import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.Orders
import com.deuktemsiru.domain.Review
import com.deuktemsiru.domain.Store
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface ReviewRepository : JpaEntityStore<Review, Long> {
    fun findByStoreAndIsDeletedFalseOrderByCreatedAtDesc(store: Store, pageable: Pageable): List<Review>
    fun existsByConsumerAndOrder(consumer: Member, order: Orders): Boolean

    @Query(
        """
        SELECT COUNT(r) as reviewCount, AVG(r.rating) as ratingAvg
        FROM Review r
        WHERE r.store = :store AND r.isDeleted = false
        """
    )
    fun summarizeRatingByStore(store: Store): ReviewRatingSummary
}
