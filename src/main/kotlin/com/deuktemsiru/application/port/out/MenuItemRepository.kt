package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.MenuItem
import com.deuktemsiru.domain.Store

interface MenuItemRepository : EntityStore<MenuItem, Long> {
    fun findByStore(store: Store): List<MenuItem>
    fun findByStoreAndIsActiveTrue(store: Store): List<MenuItem>
}
