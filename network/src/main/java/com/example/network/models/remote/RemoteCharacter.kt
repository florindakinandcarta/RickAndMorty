package com.example.network.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class RemoteCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: ArrayList<String>,
    val url: String,
    val created: String
)
