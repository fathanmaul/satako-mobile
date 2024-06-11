package dev.capstone.satako_mobile.data.model.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val author: String,
    val imageUrl: String,
    val description: String
) : Parcelable
