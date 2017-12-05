package com.kotato.context.ecommerce.modules.cart.domain

import com.kotato.context.ecommerce.modules.cart.domain.create.CartCreatedEvent
import com.kotato.context.ecommerce.modules.user.domain.UserId
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import java.time.ZonedDateTime

@Aggregate
class Cart {

    @AggregateIdentifier
    lateinit var id: CartId
        private set
    lateinit var userId: UserId
        private set

    @EventSourcingHandler
    fun on(event: CartCreatedEvent) {
        this.id = event.aggregateId().let { CartId.fromString(it) }
        this.userId = event.userId.let { UserId.fromString(it) }
    }

    companion object {
        fun create(id: CartId, userId: UserId) {
            AggregateLifecycle.apply(CartCreatedEvent(aggregateId = id.asString(),
                                                      occurredOn = ZonedDateTime.now(),
                                                      userId = userId.asString()))
        }
    }
}