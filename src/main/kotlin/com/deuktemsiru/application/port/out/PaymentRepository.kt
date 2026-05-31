package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Orders
import com.deuktemsiru.domain.Payment
import java.util.Optional

interface PaymentRepository : EntityStore<Payment, Long> {
    fun findFirstByOrderOrderByPaymentIdDesc(order: Orders): Optional<Payment>

    
    fun findLatestByOrderIds( orderIds: List<Long>): List<Payment>
}
