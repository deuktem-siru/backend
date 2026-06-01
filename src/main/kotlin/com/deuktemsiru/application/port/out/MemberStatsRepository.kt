package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.MemberStats
import java.util.Optional

interface MemberStatsRepository : EntityStore<MemberStats, Long> {
    fun findByMember(member: Member): Optional<MemberStats>
}
