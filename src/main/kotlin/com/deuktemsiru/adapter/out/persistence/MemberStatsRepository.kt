package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.MemberStats
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MemberStatsRepository : JpaEntityStore<MemberStats, Long>, com.deuktemsiru.application.port.out.MemberStatsRepository {
    override fun findByMember(member: Member): Optional<MemberStats>
}
