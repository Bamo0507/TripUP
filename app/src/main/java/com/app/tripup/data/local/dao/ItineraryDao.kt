package com.app.tripup.data.local.dao

import androidx.room.*
import com.app.tripup.data.local.entities.Itinerary

//Dao para los itinerarios
@Dao
interface ItineraryDao {
    //insertar datos en una tabla de la base de datos
    //Se inserta un itinerario y se manda un objeto de tipo Itinerary
    //El onConflict es para que se reemplace un itinerario si por alguna razón se genera uno con el mismo ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItinerary(itinerary: Itinerary): Long

    //Busca en la base de datos todos los itinerarios y los devuelve
    @Query("SELECT * FROM itineraries")
    suspend fun getAllItineraries(): List<Itinerary>

    //Busca en la base de datos el itinerario cuyo ID sea el que se le pase y lo devuelve
    @Query("SELECT * FROM itineraries WHERE id = :id")
    suspend fun getItineraryById(id: Int): Itinerary?

    //Elimina un itinerario de la base de datos, se evalúa su implementación aún
    @Delete
    suspend fun deleteItinerary(itinerary: Itinerary)
}