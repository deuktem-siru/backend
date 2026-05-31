package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.CategoryType
import com.deuktemsiru.domain.Member
import com.deuktemsiru.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface StoreRepository : JpaEntityStore<Store, Long>, com.deuktemsiru.application.port.out.StoreRepository {
    override fun findByOwner(owner: Member): Optional<Store>
    override fun existsByOwner(owner: Member): Boolean

    @Query("SELECT DISTINCT s FROM Store s JOIN s.categories c WHERE c.category = :category")
    override fun findByCategory(@Param("category") category: CategoryType): List<Store>
}
