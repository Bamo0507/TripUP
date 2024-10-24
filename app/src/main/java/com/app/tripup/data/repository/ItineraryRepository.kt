package com.app.tripup.data.repository

import com.app.tripup.data.local.dao.ItineraryDao
import com.app.tripup.data.local.entities.Itinerary

class ItineraryRepository(private val itineraryDao: ItineraryDao) {
    suspend fun insertItinerary(itinerary: Itinerary) = itineraryDao.insertItinerary(itinerary)
    suspend fun getAllItineraries(): List<Itinerary> = itineraryDao.getAllItineraries()
    suspend fun getItineraryById(id: Int): Itinerary? = itineraryDao.getItineraryById(id)
    suspend fun deleteItinerary(itinerary: Itinerary) = itineraryDao.deleteItinerary(itinerary)
}