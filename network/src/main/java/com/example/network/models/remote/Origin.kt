package com.example.network.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class Origin(
    val name: String,
    val url: String
)
