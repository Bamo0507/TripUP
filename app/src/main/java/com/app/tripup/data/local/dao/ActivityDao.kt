package com.app.tripup.data.local.dao

import androidx.room.*
import com.app.tripup.data.local.entities.Activity

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity)

    @Query("SELECT * FROM activities WHERE dayItineraryId = :dayItineraryId")
    suspend fun getActivitiesForDay(dayItineraryId: Int): List<Activity>

    @Delete
    suspend fun deleteActivity(activity: Activity)

    @Query("SELECT COUNT(*) FROM Activities WHERE dayItineraryId = :dayItineraryId")
    suspend fun getActivityCountForDay(dayItineraryId: Int): Int

}