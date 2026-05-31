package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.*
import com.deuktemsiru.application.port.out.PageQuery
import com.deuktemsiru.application.port.out.EntityStore
import org.springframework.stereotype.Component
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.*
import com.deuktemsiru.application.port.out.ReviewRatingSummary
import java.util.Optional

@Component
class OrderPersistenceAdapter(private val repository: OrderRepository) :
    com.deuktemsiru.application.port.out.OrderRepository, EntityStore<Orders, Long> by repository {
    override fun findByCriteria(store: Store, status: OrderStatus?, start: LocalDateTime?, end: LocalDateTime?): List<Orders> =
        repository.findByCriteria(store, status, start, end)
    override fun findByCriteria(store: Store, status: OrderStatus?, start: LocalDateTime?, end: LocalDateTime?, pageable: PageQuery): List<Orders> =
        repository.findByCriteria(store, status, start, end, pageable.asPageable())
    override fun findByConsumerOrderByCreatedAtDesc(consumer: Member): List<Orders> =
        repository.findByConsumerOrderByCreatedAtDesc(consumer)
    override fun findByConsumerOrderByCreatedAtDesc(consumer: Member, pageable: PageQuery): List<Orders> =
        repository.findByConsumerOrderByCreatedAtDesc(consumer, pageable.asPageable())
    override fun findByConsumerAndStatusOrderByCreatedAtDesc(consumer: Member, status: OrderStatus, pageable: PageQuery): List<Orders> =
        repository.findByConsumerAndStatusOrderByCreatedAtDesc(consumer, status, pageable.asPageable())
    override fun findByStoreOrderByCreatedAtDesc(store: Store): List<Orders> =
        repository.findByStoreOrderByCreatedAtDesc(store)
    override fun countByStoreAndStatus(store: Store, status: OrderStatus): Long =
        repository.countByStoreAndStatus(store, status)
    override fun existsByPickupCode(pickupCode: String): Boolean =
        repository.existsByPickupCode(pickupCode)
    override fun existsActiveOrderForProduct(store: Store, statuses: List<OrderStatus>, product: Product): Boolean =
        repository.existsActiveOrderForProduct(store, statuses, product)
    override fun findByIdForUpdate(id: Long): Optional<Orders> =
        repository.findByIdForUpdate(id)
    override fun findByPickupCodeForUpdate(pickupCode: String): Orders? =
        repository.findByPickupCodeForUpdate(pickupCode)
    override fun sumTotalPriceByStoreAndStatusBetween(store: Store, status: OrderStatus, start: LocalDateTime, end: LocalDateTime): Long =
        repository.sumTotalPriceByStoreAndStatusBetween(store, status, start, end)
}

@Component
class ReviewPersistenceAdapter(private val repository: ReviewRepository) :
    com.deuktemsiru.application.port.out.ReviewRepository, EntityStore<Review, Long> by repository {
    override fun findByStoreAndIsDeletedFalseOrderByCreatedAtDesc(store: Store, pageable: PageQuery): List<Review> =
        repository.findByStoreAndIsDeletedFalseOrderByCreatedAtDesc(store, pageable.asPageable())
    override fun existsByConsumerAndOrder(consumer: Member, order: Orders): Boolean =
        repository.existsByConsumerAndOrder(consumer, order)
    override fun summarizeRatingByStore(store: Store): ReviewRatingSummary =
        repository.summarizeRatingByStore(store)
}

private fun PageQuery.asPageable() = PageRequest.of(pageNumber, pageSize, if (newestFirst) Sort.by(Sort.Direction.DESC, "createdAt") else Sort.unsorted())
