package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.Store
import com.deuktemsiru.domain.Wishlist
import java.util.Optional

interface WishlistRepository : EntityStore<Wishlist, Long> {
    fun findByMember(member: Member): List<Wishlist>
    fun findByStore(store: Store): List<Wishlist>
    fun findByMemberAndStoreIn(member: Member, stores: List<Store>): List<Wishlist>
    fun findByMemberAndStore(member: Member, store: Store): Optional<Wishlist>
    fun existsByMemberAndStore(member: Member, store: Store): Boolean
    fun countByStore(store: Store): Long
}
