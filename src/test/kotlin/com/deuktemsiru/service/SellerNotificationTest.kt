package com.deuktemsiru.service

import com.deuktemsiru.adapter.out.integration.*
import com.deuktemsiru.application.service.*

import com.deuktemsiru.application.dto.SendNotificationRequest
import com.deuktemsiru.domain.*
import com.deuktemsiru.application.port.out.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class SellerNotificationTest {
    private val notifications = mock(NotificationRepository::class.java)
    private val wishlists = mock(WishlistRepository::class.java)
    private val orders = mock(OrderRepository::class.java)
    private val ownership = mock(StoreOwnershipService::class.java)
    private val fcm = mock(FcmService::class.java)
    private val seller = member(10L)
    private val store = Store(storeId = 1L, owner = seller, name = "가게", address = "시흥", latitude = 37.0, longitude = 126.0)
    private val clock = Clock.fixed(Instant.parse("2026-10-21T10:00:00Z"), ZoneOffset.UTC)
    private val service = SellerAppService(
        mock(MenuItemRepository::class.java), mock(ProductRepository::class.java), orders, notifications,
        mock(BusinessInfoRepository::class.java), wishlists, mock(MemberService::class.java),
        mock(ImageStorage::class.java), fcm, ownership, clock,
    )

    private fun member(id: Long, active: Boolean = true) = Member(
        memberId = id, providerId = "$id", email = "$id@test.com", name = "회원$id", nickname = "회원$id", status = active,
    )

    @Test
    fun `sends to every distinct active recipient and returns the first saved notification`() {
        val buyer = member(1L)
        val other = member(2L)
        `when`(ownership.findSellerStore(10L)).thenReturn(store)
        `when`(wishlists.findByStore(store)).thenReturn(listOf(
            Wishlist(member = buyer, store = store), Wishlist(member = member(3L, active = false), store = store),
        ))
        `when`(orders.findByStoreOrderByCreatedAtDesc(store)).thenReturn(listOf(
            Orders(consumer = buyer, store = store), Orders(consumer = other, store = store),
        ))
        val saved = mutableListOf<Notification>()
        val placeholder = Notification(member = buyer, type = NotificationType.EVENT, title = "", body = "")
        `when`(notifications.save(any(Notification::class.java) ?: placeholder)).thenAnswer { invocation ->
            val input = invocation.getArgument<Notification>(0)
            Notification(notificationId = saved.size + 100L, member = input.member, relatedStoreId = input.relatedStoreId,
                type = input.type, title = input.title, body = input.body, createdAt = LocalDateTime.now(clock))
                .also(saved::add)
        }

        val result = service.sendNotification(10L, SendNotificationRequest(message = "마감 할인"))

        assertEquals(listOf(1L, 2L), saved.map { it.member.memberId })
        assertEquals(2, result.recipientCount)
        assertEquals(100L, result.id)
        assertEquals("2026-10-21T10:00", result.sentAt)
        verify(fcm).sendToMember(1L, "가게", "마감 할인")
        verify(fcm).sendToMember(2L, "가게", "마감 할인")
        verifyNoMoreInteractions(fcm)
    }

    @Test
    fun `history groups the same message within a minute and orders groups by latest timestamp`() {
        val buyer = member(1L)
        fun notification(id: Long, body: String, time: String) = Notification(
            notificationId = id, member = buyer, type = NotificationType.EVENT,
            title = store.name, body = body, createdAt = LocalDateTime.parse(time),
        )
        `when`(ownership.findSellerStore(10L)).thenReturn(store)
        `when`(notifications.findByRelatedStoreIdAndTitleOrderByCreatedAtDesc(1L, "가게")).thenReturn(listOf(
            notification(4L, "할인", "2026-10-21T10:02:00"),
            notification(3L, "할인", "2026-10-21T10:01:30"),
            notification(2L, "입고", "2026-10-21T10:01:20"),
            notification(1L, "할인", "2026-10-21T10:01:00"),
        ))

        val history = service.getSellerNotifications(10L)

        assertEquals(listOf(4L, 3L, 2L), history.map { it.id })
        assertEquals(listOf(1, 2, 1), history.map { it.recipientCount })
    }
}
