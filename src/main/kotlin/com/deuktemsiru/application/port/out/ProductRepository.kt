package com.deuktemsiru.application.port.out

import com.deuktemsiru.domain.Product
import com.deuktemsiru.domain.ProductStatus
import com.deuktemsiru.domain.Store
import java.time.LocalDate
import java.util.Optional

interface StoreProductCount {
    val storeId: Long
    val productCount: Long
}

interface ProductRepository : EntityStore<Product, Long> {
    fun findByStore(store: Store): List<Product>
    fun findByStoreAndAvailableDateOrderByCreatedAtDesc(store: Store, date: LocalDate): List<Product>
    fun findByStoreInAndAvailableDateAndStatusAndQuantityRemainingGreaterThan(
        stores: List<Store>,
        date: LocalDate,
        status: ProductStatus,
        quantityRemaining: Int,
    ): List<Product>
    fun findByStoreAndAvailableDateAndStatusAndQuantityRemainingGreaterThan(
        store: Store,
        date: LocalDate,
        status: ProductStatus,
        quantityRemaining: Int,
    ): List<Product>
    fun findByStoreInAndAvailableDateAndStatus(stores: List<Store>, date: LocalDate, status: ProductStatus): List<Product>
    fun findByStoreAndAvailableDateAndStatus(store: Store, date: LocalDate, status: ProductStatus): List<Product>
    fun countByStoreAndAvailableDateAndStatus(store: Store, date: LocalDate, status: ProductStatus): Int

    
    fun countAvailableProductsByStoreIds(
         storeIds: List<Long>,
         date: LocalDate,
         status: ProductStatus = ProductStatus.AVAILABLE,
    ): List<StoreProductCount>

    
    
    fun findByIdForUpdate( id: Long): Optional<Product>
}
