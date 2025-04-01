package com.example.network.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val url: String
)
