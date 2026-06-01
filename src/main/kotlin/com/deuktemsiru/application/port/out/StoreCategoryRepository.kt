package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.CategoryType
import com.deuktemsiru.domain.Store
import com.deuktemsiru.domain.StoreCategory

interface StoreCategoryRepository : EntityStore<StoreCategory, Long> {
    fun findByStore(store: Store): List<StoreCategory>
    fun findByCategory(category: CategoryType): List<StoreCategory>
}
