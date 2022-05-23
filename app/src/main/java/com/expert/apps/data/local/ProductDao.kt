package com.expert.apps.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

import com.expert.apps.ui.products.model.Product
import com.expert.apps.utils.room.BaseDao

@Dao
abstract class ProductDao : BaseDao<Product> {
    @Query("SELECT * FROM Product")
    abstract fun getProductCash(): LiveData<List<Product>>


}

