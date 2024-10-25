package com.app.tripup.data.source

import com.app.tripup.data.model.Place
import com.app.tripup.data.model.PlaceCategory

class SearchPlaceDb {
    private val places: List<Place> = listOf(
        // HOTELS
        Place(
            id = 1,
            name = "Hotel Casa Santo Domingo",
            location = "Antigua Guatemala, Sacatepéquez",
            imageUrl = "",
            description = "Un hotel boutique de lujo ubicado en un convento colonial restaurado, con museo y spa.",
            category = PlaceCategory.HOTELS
        ),
        Place(
            id = 2,
            name = "Westin Camino Real",
            location = "Ciudad de Guatemala, Guatemala",
            imageUrl = "",
            description = "Hotel icónico en la Zona 10 con instalaciones de primer nivel, piscina y canchas deportivas.",
            category = PlaceCategory.HOTELS
        ),

        // RESTAURANTS
        Place(
            id = 3,
            name = "El Tenedor del Cerro",
            location = "Antigua Guatemala, Sacatepéquez",
            imageUrl = "",
            description = "Restaurante con vistas espectaculares y menú gourmet, ubicado en una colina sobre Antigua.",
            category = PlaceCategory.RESTAURANTS
        ),
        Place(
            id = 4,
            name = "Mercado Central",
            location = "Ciudad de Guatemala, Zona 1",
            imageUrl = "",
            description = "Comida tradicional guatemalteca con una experiencia auténtica en el corazón de la capital.",
            category = PlaceCategory.RESTAURANTS
        ),

        // ACTIVITIES
        Place(
            id = 5,
            name = "Parque Nacional Tikal",
            location = "Petén, Guatemala",
            imageUrl = "",
            description = "Uno de los sitios arqueológicos más importantes de Mesoamérica, rodeado de selva tropical.",
            category = PlaceCategory.ACTIVITIES
        ),
        Place(
            id = 6,
            name = "Lago de Atitlán",
            location = "Sololá, Guatemala",
            imageUrl = "",
            description = "Lago rodeado de volcanes y pueblos mayas, considerado uno de los más bellos del mundo.",
            category = PlaceCategory.ACTIVITIES
        ),
        Place(
            id = 7,
            name = "Volcán de Pacaya",
            location = "Escuintla, Guatemala",
            imageUrl = "",
            description = "Un volcán activo donde se pueden hacer caminatas y asar malvaviscos sobre la lava caliente.",
            category = PlaceCategory.ACTIVITIES
        ),

        // DRINKS
        Place(
            id = 8,
            name = "Las Cañas Bar",
            location = "Zona Viva, Ciudad de Guatemala",
            imageUrl = "",
            description = "Bar moderno con música en vivo y cocteles exclusivos, ubicado en la vibrante Zona Viva.",
            category = PlaceCategory.DRINKS
        ),
        Place(
            id = 9,
            name = "Café Sky",
            location = "Antigua Guatemala, Sacatepéquez",
            imageUrl = "",
            description = "Terraza con vistas espectaculares al volcán Agua y excelente selección de bebidas y café.",
            category = PlaceCategory.DRINKS
        )
    )

    fun getPlacesBySearch(query: String): List<Place> {
        return places.filter {
            it.location.contains(query, ignoreCase = true) || it.name.contains(query, ignoreCase = true)
        }
    }

    fun getPlaceById(id: Int): Place? {
        return places.firstOrNull { it.id == id }
    }
}
