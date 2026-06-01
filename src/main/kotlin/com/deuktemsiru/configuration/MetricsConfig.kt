package com.deuktemsiru.configuration

import com.deuktemsiru.adapter.out.persistence.MemberRepository
import com.deuktemsiru.adapter.out.persistence.OrderRepository
import com.deuktemsiru.adapter.out.persistence.StoreRepository
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.MeterBinder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetricsConfig {

    @Bean
    fun businessMetrics(
        orderRepository: OrderRepository,
        storeRepository: StoreRepository,
        memberRepository: MemberRepository,
    ): MeterBinder = MeterBinder { registry: MeterRegistry ->
        fun gauge(name: String, description: String, count: () -> Long) {
            Gauge.builder(name) { count().toDouble() }
                .description(description)
                .register(registry)
        }

        gauge("deuktemsiru.orders.total", "Total orders") { orderRepository.count() }
        gauge("deuktemsiru.stores.total", "Total stores") { storeRepository.count() }
        gauge("deuktemsiru.members.total", "Total members") { memberRepository.count() }
    }
}
