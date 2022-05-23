package com.expert.apps.data.repository

import com.expert.apps.data.local.ProductDatabase
import com.expert.apps.data.remote.ApiService
import com.expert.apps.ui.products.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService, private val productDB: ProductDatabase) {

    suspend fun getProduct() = apiService.getProducts()





   suspend fun insertProductDB(ProductResponse: Product) = productDB.productDao().insert(ProductResponse)
           fun getProductCash() = productDB.productDao().getProductCash()
    suspend fun removeProduct(city: Product) = productDB.productDao().delete(city)


}