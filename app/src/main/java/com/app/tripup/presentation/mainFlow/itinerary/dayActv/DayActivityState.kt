package com.app.tripup.presentation.mainFlow.itinerary.dayActv

import com.app.tripup.data.local.entities.Activity

data class DayActivityState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
