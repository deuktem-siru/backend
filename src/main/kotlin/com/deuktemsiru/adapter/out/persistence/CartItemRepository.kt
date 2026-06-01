package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.CartItem
import com.deuktemsiru.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemRepository : JpaEntityStore<CartItem, Long>, com.deuktemsiru.application.port.out.CartItemRepository {
    override fun findByMemberOrderByCreatedAtAsc(member: Member): List<CartItem>
    // S5: memberId 로 직접 조회하여 불필요한 Member 엔티티 로딩 제거
    override fun findByMemberMemberIdOrderByCreatedAtAsc(memberId: Long): List<CartItem>
    override fun deleteByMember(member: Member)
}
