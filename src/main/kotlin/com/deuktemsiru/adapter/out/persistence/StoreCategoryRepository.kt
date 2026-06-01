package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.CategoryType
import com.deuktemsiru.domain.Store
import com.deuktemsiru.domain.StoreCategory
import org.springframework.data.jpa.repository.JpaRepository

interface StoreCategoryRepository : JpaEntityStore<StoreCategory, Long>, com.deuktemsiru.application.port.out.StoreCategoryRepository {
    override fun findByStore(store: Store): List<StoreCategory>
    override fun findByCategory(category: CategoryType): List<StoreCategory>
}
