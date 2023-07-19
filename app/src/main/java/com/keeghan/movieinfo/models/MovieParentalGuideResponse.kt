package com.keeghan.movieinfo.models

import kotlinx.serialization.Serializable


@Serializable
data class MovieParentalGuideResponse(
    val parentalguide: List<ParentalGuide>
)

@Serializable
data class ParentalGuide(
    val items: List<Item>,
    val label: String,
    val severityVotes: SeverityVotes
) {
    @Serializable
    data class Item(
        val hasProfanity: Boolean,
        val id: String,
        val isSpoiler: Boolean,
        val language: String,
        val text: String
    )

    @Serializable
    data class SeverityVotes(
        val mildVotes: Int,
        val moderateVotes: Int,
        val noneVotes: Int,
        val severeVotes: Int,
        val status: String
    )
}