package com.app.tripup.data.repository

import com.app.tripup.data.local.dao.DayItineraryDao
import com.app.tripup.data.local.entities.DayItinerary

class DayItineraryRepository(private val dayItineraryDao: DayItineraryDao) {
    suspend fun insertDayItinerary(dayItinerary: DayItinerary) {
        dayItineraryDao.insertDayItinerary(dayItinerary)
    }

    suspend fun getDaysForItinerary(itineraryId: Int): List<DayItinerary> {
        return dayItineraryDao.getDaysForItinerary(itineraryId)
    }

    suspend fun getDayItineraryById(id: Int): DayItinerary? = dayItineraryDao.getDayItineraryById(id)
    suspend fun deleteDayItinerary(dayItinerary: DayItinerary) = dayItineraryDao.deleteDayItinerary(dayItinerary)
}