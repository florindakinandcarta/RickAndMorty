package com.example.network.models.domain

data class EpisodePage(
    val info: Info,
    val episode: List<Episode>
) {
    data class Info(
        val count: Int,
        val pages: Int,
        val next: String?,
        val prev: String?
    )
}