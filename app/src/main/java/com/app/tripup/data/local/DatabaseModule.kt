package com.app.tripup.data.local

import android.content.Context
import androidx.room.Room

object DatabaseModule {

    @Volatile
    private var INSTANCE: TripUpDatabase? = null

    fun getDatabase(context: Context): TripUpDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TripUpDatabase::class.java,
                "tripup_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
