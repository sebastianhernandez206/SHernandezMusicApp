package com.example.shernandezmusicapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val albumId: String)
