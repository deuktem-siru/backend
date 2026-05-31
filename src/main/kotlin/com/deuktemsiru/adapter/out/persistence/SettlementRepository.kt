package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.Settlement
import com.deuktemsiru.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface SettlementRepository : JpaEntityStore<Settlement, Long>, com.deuktemsiru.application.port.out.SettlementRepository {
    override fun findByStoreAndPeriodEndGreaterThanEqualAndPeriodStartLessThanEqualOrderByPeriodStartDesc(
        store: Store,
        periodStart: LocalDate,
        periodEnd: LocalDate,
    ): List<Settlement>
    override fun findFirstByStoreAndPeriodStartAndPeriodEndAndStatus(
        store: Store,
        periodStart: LocalDate,
        periodEnd: LocalDate,
        status: com.deuktemsiru.domain.SettlementStatus,
    ): Settlement?
}
