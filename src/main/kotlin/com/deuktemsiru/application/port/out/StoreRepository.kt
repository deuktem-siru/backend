package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.CategoryType
import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.Store
import java.util.Optional

interface StoreRepository : EntityStore<Store, Long> {
    fun findByOwner(owner: Member): Optional<Store>
    fun existsByOwner(owner: Member): Boolean

    
    fun findByCategory( category: CategoryType): List<Store>
}
