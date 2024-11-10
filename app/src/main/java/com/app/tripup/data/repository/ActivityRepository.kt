package com.app.tripup.data.repository

import com.app.tripup.data.local.dao.ActivityDao
import com.app.tripup.data.local.entities.Activity

//El repositorio permite el acceso a la base de datos local sin tener que estar interactuando directamente con el DAO
//Como parámetro recibe una instancia de ActivityDao
class ActivityRepository(private val activityDao: ActivityDao) {
    //Se declaran todas las funciones que se incluyeron en el DAO
    suspend fun insertActivity(activity: Activity) = activityDao.insertActivity(activity)
    suspend fun getActivitiesForDay(dayItineraryId: Int): List<Activity> = activityDao.getActivitiesForDay(dayItineraryId)
    suspend fun deleteActivity(activity: Activity) = activityDao.deleteActivity(activity) //Se debate su implementación
    suspend fun getActivityCountForDay(dayItineraryId: Int): Int = activityDao.getActivityCountForDay(dayItineraryId)
}