package com.expert.apps.data.remote


import com.expert.apps.ui.products.model.responeProduct
import retrofit2.http.*
import retrofit2.Response

interface ApiService {

    @GET("/products")
    suspend fun getProducts():  Response<responeProduct>




}