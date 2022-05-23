package com.expert.apps.ui.products.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Keep
@Parcelize
data class Product(
    @PrimaryKey
    var id: Int = 0,
    val brand: String?,
    val category: String?,
    val description: String?,
    val discountPercentage: Double?,
    val price: Int?,
    val rating: Double?,
    val stock: Int?,
    val thumbnail: String?,
    val title: String?,
    var quantity: Int=1,
    var data: Int?
): Parcelable