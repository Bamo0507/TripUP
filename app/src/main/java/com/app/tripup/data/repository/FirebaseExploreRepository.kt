package com.app.tripup.data.repository

import com.app.tripup.data.model.Place
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.tasks.await
import java.util.Locale

class FirebasePlaceRepository {
    //Se genera una instancia de Firebase Realtime Database
    private val database = FirebaseDatabase.getInstance()
    //Se apunta a la columna "places", es el key donde se tienen todos los lugares
    private val placesRef = database.getReference("places")

    //Se buscan lugares comparando contra la ubicación, nombre de lugar, y el país
    suspend fun searchPlaces(query: String): List<Place> {
        val snapshot = placesRef.get().await()
        val places = mutableListOf<Place>()

        //Limpia el query que se mandó dejando todo en minúsculas y que no hayan problemas
        val cleanedQuery = query.trim().lowercase(Locale.getDefault())

        //Devuelve todos los nodos hijos de placesRef
        //Itera sobre cada país que se ha definido
        snapshot.children.forEach { countrySnapshot ->
            //Obtiene el key del nodo que se evalúa y quita espacios
            val countryName = countrySnapshot.key?.trim() ?: ""
            //Limpia el nombre del país dejando todo en minúsculas y que no haya problemas
            val cleanedCountryName = countryName.lowercase(Locale.getDefault())
            //Deserealiza el snapshot para que se maneje como una lista de Place
            val placeList = countrySnapshot.getValue(object : GenericTypeIndicator<List<Place>>() {})
            //Si placeList no es nulo (sí existe en la data base), itera sobre cada place de la lista
            placeList?.forEach { place ->
                val placeWithCountry = place.copy(country = countryName) //Coloca a cada Place el país indicando que country es igual a countyname
                val nameMatch = place.name.lowercase(Locale.getDefault()).contains(cleanedQuery) //Compara el nombre del lugar con el query
                val locationMatch = place.location.lowercase(Locale.getDefault()).contains(cleanedQuery) //Compara la ubicación del lugar con el query
                val countryMatch = cleanedCountryName.contains(cleanedQuery) //El nombre del país que ya se limpio, se compara con el query
                if (nameMatch || locationMatch || countryMatch) { //Si en algunos de los 3 casos se cumple, se agrega el lugar a la lista
                    places.add(placeWithCountry)
                }
            }
        }
        return places //Devolvemos la lista de lugares que se encontraron
    }

    //Obtiene una lista de todos los lugares de un país
    suspend fun getPlacesByCountry(countryName: String): List<Place> {
        //Navega al nodo país que corresponda y de forma asíncrona
        val countrySnapshot = placesRef.child(countryName).get().await()
        //Convierte el snapshot en una lista de lugares
        val placeList = countrySnapshot.getValue(object : GenericTypeIndicator<List<Place>>() {})
        //Asigna countryname al campo de cada lugar
        return placeList?.map { it.copy(country = countryName) } ?: emptyList()
    }

    //Obtiene un lugar por su id y su país
    //En base de datos Places, tiene una lista de países, que tiene una lista de IDs referentes a lugares
    suspend fun getPlaceByIdAndCountry(id: Int, countryName: String): Place? {
        //Obtiene todos los lugares que tienen el país que se mandó como nodo principal
        val countrySnapshot = placesRef.child(countryName).get().await()
        //Convierte el snapshot en una lista de lugares
        val placeList = countrySnapshot.getValue(object : GenericTypeIndicator<List<Place>>() {})
        //Devuelve el lugar que tenga el id que se mandó como parámetro
        val place = placeList?.firstOrNull { it.id == id }
        return place?.copy(country = countryName) //Si se encontró el place con el id indicado, crea una copia del lugar y le pasa countryName al campo country
    }

}
