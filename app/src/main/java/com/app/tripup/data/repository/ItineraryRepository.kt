package com.app.tripup.data.repository

import com.app.tripup.data.local.dao.ItineraryDao
import com.app.tripup.data.local.entities.Itinerary

////El repositorio permite el acceso a la base de datos local sin tener que estar interactuando directamente con el DAO
//Como parámetro recibe una instancia de ItineraryDao
class ItineraryRepository(private val itineraryDao: ItineraryDao) {
    suspend fun insertItinerary(itinerary: Itinerary) = itineraryDao.insertItinerary(itinerary)
    suspend fun getAllItineraries(): List<Itinerary> = itineraryDao.getAllItineraries()
    suspend fun getItineraryById(id: Int): Itinerary? = itineraryDao.getItineraryById(id)
    suspend fun deleteItinerary(itinerary: Itinerary) = itineraryDao.deleteItinerary(itinerary)
}