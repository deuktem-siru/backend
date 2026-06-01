package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Member
import java.util.Optional

interface MemberRepository : EntityStore<Member, Long> {
    fun findByEmail(email: String): Optional<Member>
    fun findByProviderAndProviderId(provider: com.deuktemsiru.domain.MemberProvider, providerId: String): Optional<Member>

    
    
    fun findByIdForUpdate( id: Long): Optional<Member>
}
