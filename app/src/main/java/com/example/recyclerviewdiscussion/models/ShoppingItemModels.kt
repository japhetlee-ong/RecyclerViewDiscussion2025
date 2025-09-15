package com.example.recyclerviewdiscussion.models

data class ShoppingItemModels(
    val id: Int,
    var name: String,
    var quantity: Int,
    var isBought: Boolean = false
)