package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.Orders
import com.deuktemsiru.domain.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface PaymentRepository : JpaEntityStore<Payment, Long>, com.deuktemsiru.application.port.out.PaymentRepository {
    override fun findFirstByOrderOrderByPaymentIdDesc(order: Orders): Optional<Payment>

    @Query(
        """
        select p from Payment p
        where p.order.orderId in :orderIds
          and p.paymentId in (
            select max(p2.paymentId)
            from Payment p2
            where p2.order.orderId in :orderIds
            group by p2.order.orderId
          )
        """
    )
    override fun findLatestByOrderIds(@Param("orderIds") orderIds: List<Long>): List<Payment>
}
