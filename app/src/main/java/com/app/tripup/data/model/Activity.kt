package com.app.tripup.data.model

import java.time.LocalTime

data class Activity(
    val id: Int,
    val name: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)
