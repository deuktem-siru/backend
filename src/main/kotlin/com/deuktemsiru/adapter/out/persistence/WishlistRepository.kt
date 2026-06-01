package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.Store
import com.deuktemsiru.domain.Wishlist
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface WishlistRepository : JpaEntityStore<Wishlist, Long>, com.deuktemsiru.application.port.out.WishlistRepository {
    override fun findByMember(member: Member): List<Wishlist>
    override fun findByStore(store: Store): List<Wishlist>
    override fun findByMemberAndStoreIn(member: Member, stores: List<Store>): List<Wishlist>
    override fun findByMemberAndStore(member: Member, store: Store): Optional<Wishlist>
    override fun existsByMemberAndStore(member: Member, store: Store): Boolean
    override fun countByStore(store: Store): Long
}
