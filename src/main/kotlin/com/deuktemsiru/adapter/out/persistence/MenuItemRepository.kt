package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.domain.MenuItem
import com.deuktemsiru.domain.Store
import org.springframework.data.jpa.repository.JpaRepository

interface MenuItemRepository : JpaEntityStore<MenuItem, Long>, com.deuktemsiru.application.port.out.MenuItemRepository {
    override fun findByStore(store: Store): List<MenuItem>
    override fun findByStoreAndIsActiveTrue(store: Store): List<MenuItem>
}
