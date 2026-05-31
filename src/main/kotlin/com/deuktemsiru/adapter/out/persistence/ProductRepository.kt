package com.deuktemsiru.adapter.out.persistence

import com.deuktemsiru.application.port.out.StoreProductCount
import com.deuktemsiru.domain.Product
import com.deuktemsiru.domain.ProductStatus
import com.deuktemsiru.domain.Store
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.Optional


interface ProductRepository : JpaEntityStore<Product, Long>, com.deuktemsiru.application.port.out.ProductRepository {
    override fun findByStore(store: Store): List<Product>
    override fun findByStoreAndAvailableDateOrderByCreatedAtDesc(store: Store, date: LocalDate): List<Product>
    override fun findByStoreInAndAvailableDateAndStatusAndQuantityRemainingGreaterThan(
        stores: List<Store>,
        date: LocalDate,
        status: ProductStatus,
        quantityRemaining: Int,
    ): List<Product>
    override fun findByStoreAndAvailableDateAndStatusAndQuantityRemainingGreaterThan(
        store: Store,
        date: LocalDate,
        status: ProductStatus,
        quantityRemaining: Int,
    ): List<Product>
    override fun findByStoreInAndAvailableDateAndStatus(stores: List<Store>, date: LocalDate, status: ProductStatus): List<Product>
    override fun findByStoreAndAvailableDateAndStatus(store: Store, date: LocalDate, status: ProductStatus): List<Product>
    override fun countByStoreAndAvailableDateAndStatus(store: Store, date: LocalDate, status: ProductStatus): Int

    @Query(
        """
        select p.store.storeId as storeId, coalesce(sum(p.quantityRemaining), 0) as productCount
        from Product p
        where p.store.storeId in :storeIds
          and p.availableDate = :date
          and p.status = :status
        group by p.store.storeId
        """
    )
    override fun countAvailableProductsByStoreIds(
        @Param("storeIds") storeIds: List<Long>,
        @Param("date") date: LocalDate,
        @Param("status") status: ProductStatus,
    ): List<StoreProductCount>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.productId = :id")
    override fun findByIdForUpdate(@Param("id") id: Long): Optional<Product>
}
