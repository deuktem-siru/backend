package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.CartItem
import com.deuktemsiru.domain.Member

interface CartItemRepository : EntityStore<CartItem, Long> {
    fun findByMemberOrderByCreatedAtAsc(member: Member): List<CartItem>
    // S5: memberId 로 직접 조회하여 불필요한 Member 엔티티 로딩 제거
    fun findByMemberMemberIdOrderByCreatedAtAsc(memberId: Long): List<CartItem>
    fun deleteByMember(member: Member)
}
