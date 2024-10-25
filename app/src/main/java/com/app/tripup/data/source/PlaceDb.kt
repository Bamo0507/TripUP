package com.app.tripup.data.source

import com.app.tripup.data.model.Place
import com.app.tripup.data.model.PlaceCategory

class PlaceDb {
    private val places: List<Place> = listOf(
        // HOTELS
        Place(
            id = 1,
            name = "Panama City Beach",
            location = "Panama City, Florida",
            imageUrl = "",
            description = "A picturesque beach destination with turquoise waters and white sands, popular for water sports and family vacations.",
            category = PlaceCategory.HOTELS
        ),
        Place(
            id = 2,
            name = "The Ritz-Carlton",
            location = "New York City, USA",
            imageUrl = "",
            description = "A luxury hotel offering exceptional service, located in the heart of Manhattan with views of Central Park.",
            category = PlaceCategory.HOTELS
        ),
        Place(
            id = 3,
            name = "Hotel Arts",
            location = "Barcelona, Spain",
            imageUrl = "",
            description = "A modern hotel overlooking the Mediterranean Sea, known for its architecture and proximity to Barcelona's beaches.",
            category = PlaceCategory.HOTELS
        ),

        // RESTAURANTS
        Place(
            id = 4,
            name = "Le Bernardin",
            location = "New York City, USA",
            imageUrl = "",
            description = "A top-tier French seafood restaurant, consistently awarded three Michelin stars, offering an exceptional dining experience.",
            category = PlaceCategory.RESTAURANTS
        ),
        Place(
            id = 5,
            name = "Central",
            location = "Lima, Peru",
            imageUrl = "",
            description = "Ranked among the best restaurants in the world, Central offers a culinary journey through Peru's biodiversity.",
            category = PlaceCategory.RESTAURANTS
        ),
        Place(
            id = 6,
            name = "Nobu",
            location = "Tokyo, Japan",
            imageUrl = "",
            description = "A world-renowned restaurant blending traditional Japanese flavors with modern culinary techniques.",
            category = PlaceCategory.RESTAURANTS
        ),

        // ACTIVITIES
        Place(
            id = 7,
            name = "Machu Picchu",
            location = "Cusco Region, Peru",
            imageUrl = "",
            description = "An ancient Inca citadel nestled high in the Andes Mountains, offering breathtaking views and rich history.",
            category = PlaceCategory.ACTIVITIES
        ),
        Place(
            id = 8,
            name = "Louvre Museum",
            location = "Paris, France",
            imageUrl = "",
            description = "The world's largest art museum, home to thousands of works including the Mona Lisa and the Venus de Milo.",
            category = PlaceCategory.ACTIVITIES
        ),
        Place(
            id = 9,
            name = "Mount Fuji",
            location = "Honshu, Japan",
            imageUrl = "",
            description = "The tallest mountain in Japan, an iconic symbol offering challenging hikes and scenic views.",
            category = PlaceCategory.ACTIVITIES
        ),

        // DRINKS
        Place(
            id = 10,
            name = "Sky Bar",
            location = "Bangkok, Thailand",
            imageUrl = "",
            description = "A rooftop bar with panoramic views of Bangkok, known for its luxurious ambiance and signature cocktails.",
            category = PlaceCategory.DRINKS
        ),
        Place(
            id = 11,
            name = "American Bar",
            location = "London, England, UK",
            imageUrl = "",
            description = "One of the world's oldest cocktail bars, located at The Savoy, offering classic and innovative cocktails.",
            category = PlaceCategory.DRINKS
        ),
        Place(
            id = 12,
            name = "Floridita",
            location = "Havana, Cuba",
            imageUrl = "",
            description = "A historic bar known for its daiquiris, frequented by Ernest Hemingway.",
            category = PlaceCategory.DRINKS
        ),

        // ADVENTURES
        Place(
            id = 13,
            name = "Safari Adventure",
            location = "Maasai Mara, Kenya",
            imageUrl = "",
            description = "A thrilling safari experience exploring African wildlife in their natural habitat.",
            category = PlaceCategory.ACTIVITIES
        ),
        Place(
            id = 14,
            name = "Northern Lights",
            location = "Reykjavik, Iceland",
            imageUrl = "",
            description = "A magical experience witnessing the aurora borealis dancing across the night sky.",
            category = PlaceCategory.ACTIVITIES
        ),
        Place(
            id = 15,
            name = "Hot Air Balloon Ride",
            location = "Cappadocia, Turkey",
            imageUrl = "",
            description = "Soar above the surreal landscapes of Cappadocia in a colorful hot air balloon.",
            category = PlaceCategory.ACTIVITIES
        )
    )

    fun getAllPlaces(): List<Place> = places

    fun getPlaceById(id: Int): Place? = places.firstOrNull { it.id == id }
}
