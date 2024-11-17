package com.app.tripup.data.local.dao

import androidx.room.*
import com.app.tripup.data.local.entities.DayItinerary

//Dao para un día de itinerario
@Dao
interface DayItineraryDao {
    //Eliminar un día de itinerario, aún se debate si se implementará o no
    @Delete
    suspend fun deleteDayItinerary(dayItinerary: DayItinerary)

    //Insertar un día de itinerario, se le pasa un objeto de tipo DayItinerary y lo mete a la base de datos
    @Insert
    suspend fun insertDayItinerary(dayItinerary: DayItinerary)

    //Consulta en la base de datos todos los DayItinerary Entities que tengan el mismo itineraryId que el que se mande
    @Query("SELECT * FROM day_itineraries WHERE itineraryId = :itineraryId")
    suspend fun getDaysForItinerary(itineraryId: Int): List<DayItinerary>
}