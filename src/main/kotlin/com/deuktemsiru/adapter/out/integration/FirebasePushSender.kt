package com.deuktemsiru.adapter.out.integration

import com.deuktemsiru.application.port.out.PushSender
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component

@Component
class FirebasePushSender(private val messaging: ObjectProvider<FirebaseMessaging>) : PushSender {
    override val available get() = messaging.ifAvailable != null
    override fun send(tokens: List<String>, title: String, body: String): Int {
        val sender = messaging.ifAvailable ?: return 0
        val message = MulticastMessage.builder().addAllTokens(tokens)
            .setNotification(Notification.builder().setTitle(title).setBody(body).build()).build()
        return sender.sendEachForMulticast(message).successCount
    }
}
