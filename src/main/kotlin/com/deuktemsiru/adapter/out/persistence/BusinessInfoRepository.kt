package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.BusinessInfo
import com.deuktemsiru.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface BusinessInfoRepository : JpaEntityStore<BusinessInfo, Long>, com.deuktemsiru.application.port.out.BusinessInfoRepository {
    override fun findByMember(member: Member): Optional<BusinessInfo>
}
