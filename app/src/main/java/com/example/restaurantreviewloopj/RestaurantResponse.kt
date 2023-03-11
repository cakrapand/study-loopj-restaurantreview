package com.example.restaurantreviewloopj

data class RestaurantResponse(
    val restaurant: Restaurant,
    val error: Boolean,
    val message: String
)

data class Restaurant(
    val customerReviews: List<CustomerReviewsItem>,
    val pictureId: String,
    val name: String,
    val rating: Any,
    val description: String,
    val id: String
)

data class CustomerReviewsItem(
    val date: String,
    val review: String,
    val name: String
)
