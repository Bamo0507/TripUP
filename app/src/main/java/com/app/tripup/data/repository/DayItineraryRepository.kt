package com.app.tripup.data.repository

import com.app.tripup.data.local.dao.DayItineraryDao
import com.app.tripup.data.local.entities.DayItinerary

//El repositorio permite el acceso a la base de datos local sin tener que estar interactuando directamente con el DAO
//Como parámetro recibe una instancia de DayItineraryDao
class DayItineraryRepository(private val dayItineraryDao: DayItineraryDao) {
    //Funciones declaradas en el DAO
    suspend fun insertDayItinerary(dayItinerary: DayItinerary) {
        dayItineraryDao.insertDayItinerary(dayItinerary)
    }
    suspend fun getDaysForItinerary(itineraryId: Int): List<DayItinerary> {
        return dayItineraryDao.getDaysForItinerary(itineraryId)
    }
    //Probablemente no se utilice
    suspend fun getDayItineraryById(id: Int): DayItinerary? = dayItineraryDao.getDayItineraryById(id)
    //Se evalúa su incorporación al programa
    suspend fun deleteDayItinerary(dayItinerary: DayItinerary) = dayItineraryDao.deleteDayItinerary(dayItinerary)
}