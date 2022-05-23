package com.expert.apps.data.local

import androidx.room.*
import com.expert.apps.di.ApplicationScope
import com.expert.apps.ui.products.model.Product
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters()
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    class Callback @Inject constructor(
        private val database: Provider<ProductDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()

}