package com.app.tripup.data.local

import android.content.Context
import androidx.room.Room

//Se define como Object para garantizar que solamente exista una instancia de la base de datos
object DatabaseModule {
    //Se define como @Volatile para asegurar que cualquier cambio en la variable INSTANCE
    //sea vista de inmediato por todos los  hilos
    //evita que se guarden instancias pasadas de la base de datos
    @Volatile
    private var INSTANCE: TripUpDatabase? = null

    //Se define una función para obtener una instancia de la base de datos
    //Si existe la da y si no la crea
    fun getDatabase(context: Context): TripUpDatabase {
        return INSTANCE ?: synchronized(this) { //sincronización de hilos
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TripUpDatabase::class.java,
                "tripup_database" //NOMBRE para la base de datos
            ).build() //Se construye la base de datos
            INSTANCE = instance
            instance //Se devuelve la instancia
        }
    }
}
