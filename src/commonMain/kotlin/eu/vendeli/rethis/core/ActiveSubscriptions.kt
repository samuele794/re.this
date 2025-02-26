package eu.vendeli.rethis.core

import eu.vendeli.rethis.types.interfaces.SubscriptionEventHandler
import kotlinx.coroutines.Job

class ActiveSubscriptions {
    internal val jobs = mutableMapOf<String, Job>()
    internal var eventHandler: SubscriptionEventHandler? = null

    val size get() = jobs.size

    fun unsubscribe(id: String): Boolean = jobs[id]?.let {
        it.cancel()
        jobs.remove(id)
        it.isCancelled
    } == true

    fun unsubscribeAll(): Boolean {
        jobs.forEach { unsubscribe(it.key) }
        return jobs.isEmpty()
    }

    fun setEventHandler(eventHandler: SubscriptionEventHandler) {
        this.eventHandler = eventHandler
    }

    fun isActive(id: String): Boolean = jobs[id]?.isActive == true
}
