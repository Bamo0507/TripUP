package com.app.tripup.data.repository

import com.app.tripup.data.local.dao.ActivityDao
import com.app.tripup.data.local.entities.Activity

class ActivityRepository(private val activityDao: ActivityDao) {
    suspend fun insertActivity(activity: Activity) = activityDao.insertActivity(activity)
    suspend fun getActivitiesForDay(dayItineraryId: Int): List<Activity> = activityDao.getActivitiesForDay(dayItineraryId)
    suspend fun deleteActivity(activity: Activity) = activityDao.deleteActivity(activity)
    suspend fun getActivityCountForDay(dayItineraryId: Int): Int = activityDao.getActivityCountForDay(dayItineraryId)
}