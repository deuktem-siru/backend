package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.Member
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface MemberRepository : JpaEntityStore<Member, Long>, com.deuktemsiru.application.port.out.MemberRepository {
    override fun findByEmail(email: String): Optional<Member>
    override fun findByProviderAndProviderId(provider: com.deuktemsiru.domain.MemberProvider, providerId: String): Optional<Member>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Member m where m.memberId = :id")
    override fun findByIdForUpdate(@Param("id") id: Long): Optional<Member>
}
