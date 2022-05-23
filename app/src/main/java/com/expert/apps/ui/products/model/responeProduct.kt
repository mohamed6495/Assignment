package com.expert.apps.ui.products.model


data class responeProduct(

    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)