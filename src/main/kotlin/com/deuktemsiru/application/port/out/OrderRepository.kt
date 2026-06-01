package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.OrderStatus
import com.deuktemsiru.domain.Orders
import com.deuktemsiru.domain.Product
import com.deuktemsiru.domain.Store
import com.deuktemsiru.application.port.out.PageQuery
import java.time.LocalDateTime
import java.util.Optional

interface OrderRepository : EntityStore<Orders, Long> {
    fun findByCriteria(store: Store, status: OrderStatus?, start: LocalDateTime?, end: LocalDateTime?): List<Orders>
    fun findByCriteria(store: Store, status: OrderStatus?, start: LocalDateTime?, end: LocalDateTime?, pageable: PageQuery): List<Orders>

    fun findByConsumerOrderByCreatedAtDesc(consumer: Member): List<Orders>
    fun findByConsumerOrderByCreatedAtDesc(consumer: Member, pageable: PageQuery): List<Orders>
    fun findByConsumerAndStatusOrderByCreatedAtDesc(consumer: Member, status: OrderStatus, pageable: PageQuery): List<Orders>
    fun findByStoreOrderByCreatedAtDesc(store: Store): List<Orders>
    fun countByStoreAndStatus(store: Store, status: OrderStatus): Long
    fun existsByPickupCode(pickupCode: String): Boolean

    
    fun existsActiveOrderForProduct(
         store: Store,
         statuses: List<OrderStatus>,
         product: Product,
    ): Boolean

    
    
    fun findByIdForUpdate( id: Long): Optional<Orders>

    
    
    fun findByPickupCodeForUpdate( pickupCode: String): Orders?

    
    fun sumTotalPriceByStoreAndStatusBetween(
         store: Store,
         status: OrderStatus,
         start: LocalDateTime,
         end: LocalDateTime,
    ): Long
}
