package com.app.tripup.data.model

//Enum class para los tipos de Places que se tendrán
enum class PlaceCategory {
    RESTAURANTS, HOTELS, DRINKS, ACTIVITIES
}

//Esta es la data class con la que se construirá el Place cuando venga la info de la base de datos (firebase)
data class Place(
    val id: Int = 0,
    val name: String = "",
    val location: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val category: String = "",
    val country: String = ""
) {
    //De la base de datos se recibe la category como un STRING
    //Se hace el casteo de STRING al ENUM CLASS que necesitamos
    val categoryEnum: PlaceCategory
        get() = when (category.uppercase()) { //Se le pasa UPPERCASE para que no haya problemas
            //Setea el valor a un tipo de PlaceCategory
            "RESTAURANTS" -> PlaceCategory.RESTAURANTS
            "HOTELS" -> PlaceCategory.HOTELS
            "DRINKS" -> PlaceCategory.DRINKS
            "ACTIVITIES" -> PlaceCategory.ACTIVITIES
            else -> PlaceCategory.ACTIVITIES //En caso se haya cometido un error generando la base de datos
            //es válido tomarlo como un activity siempre
        }
}
