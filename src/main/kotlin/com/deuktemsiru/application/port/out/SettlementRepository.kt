package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Settlement
import com.deuktemsiru.domain.Store
import java.time.LocalDate

interface SettlementRepository : EntityStore<Settlement, Long> {
    fun findByStoreAndPeriodEndGreaterThanEqualAndPeriodStartLessThanEqualOrderByPeriodStartDesc(
        store: Store,
        periodStart: LocalDate,
        periodEnd: LocalDate,
    ): List<Settlement>
    fun findFirstByStoreAndPeriodStartAndPeriodEndAndStatus(
        store: Store,
        periodStart: LocalDate,
        periodEnd: LocalDate,
        status: com.deuktemsiru.domain.SettlementStatus,
    ): Settlement?
}
