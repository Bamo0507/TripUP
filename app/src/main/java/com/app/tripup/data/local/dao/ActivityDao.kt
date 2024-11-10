package com.app.tripup.data.local.dao

import androidx.room.*
import com.app.tripup.data.local.entities.Activity

//Se genera el DAO para las actividades
//Estos son los métodos con los que se interactuará con las activities
@Dao
interface ActivityDao {
    //Se trata de un query que lo que hace es que le dice a Room que guarde un activity
    //@Insert le dice que tiene que insertar el activity en la base de datos local
    //El onclifctstrategy.replace ayuda a que si se quiere generar un registro que ya exista, que se reemplace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity)

    //@Query para hacer una consulta personalizada, obtener datos
    //Lo que se dice en el parámetro es que quiero todas las activities cuyo dayItineraryId sea el que se le pase (el mismo entre ellas)
    @Query("SELECT * FROM activities WHERE dayItineraryId = :dayItineraryId")
    suspend fun getActivitiesForDay(dayItineraryId: Int): List<Activity>

    //@Delete para eliminar un activity de la base de datos
    //Se debate si se quiere implementar esto o se dejará en el estado actual
    @Delete
    suspend fun deleteActivity(activity: Activity)

    //@Query para hacer una consulta personalizada, obtener datos
    //Cuenta el número de registros que se han hecho en la tabla activities sobre el campo dayItineraryId que se mande
    @Query("SELECT COUNT(*) FROM Activities WHERE dayItineraryId = :dayItineraryId")
    suspend fun getActivityCountForDay(dayItineraryId: Int): Int

}