package mx.edu.utez.liverpooleduardo.modelos

import java.util.UUID

data class ProductDetail(
    val id: String = UUID.randomUUID().toString(),
    val brand: String,
    val name: String,
    val price: Double,
    val rating: Float,
    val reviewCount: Int,
    val isFreeShipping: Boolean = true,
    val description: String,
    val image: Int?,
    val reviews: List<Review> = emptyList(),
    val isFavorite: Boolean = false
)
