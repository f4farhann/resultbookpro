package com.resultbookpro.app.data.model

import java.util.UUID

data class UpcomingEvent(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val emoji: String,
    val type: EventType
)

enum class EventType {
    EXAM, TASK, REMINDER, OTHER
}
