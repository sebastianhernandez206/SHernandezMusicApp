package com.example.shernandezmusicapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: String? = null,
    @SerialName("_id")
    val _id: String? = null,
    val title: String,
    val artist: String,
    val description: String,
    val image: String
) {
    val displayId: String get() = id ?: _id ?: ""
}
