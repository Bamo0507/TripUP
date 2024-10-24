// DayInfoState.kt
package com.app.tripup.presentation.mainFlow.itinerary.addInfoDay

data class DayInfoState(
    val activityName: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val isFormComplete: Boolean = false,
    val errorMessage: String? = null
)
