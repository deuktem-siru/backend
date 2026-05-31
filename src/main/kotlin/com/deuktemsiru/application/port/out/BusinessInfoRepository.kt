package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.BusinessInfo
import com.deuktemsiru.domain.Member
import java.util.Optional

interface BusinessInfoRepository : EntityStore<BusinessInfo, Long> {
    fun findByMember(member: Member): Optional<BusinessInfo>
}
