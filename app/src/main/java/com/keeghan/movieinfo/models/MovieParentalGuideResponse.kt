package com.keeghan.movieinfo.models


import com.google.gson.annotations.SerializedName

data class MovieParentalGuideResponse(
    val parentalguide: List<Parentalguide>
) {
    data class Parentalguide(
        val items: List<Item>,
        val label: String,
        val severityVotes: SeverityVotes
    ) {
        data class Item(
            val hasProfanity: Boolean,
            val id: String,
            val isSpoiler: Boolean,
            val language: String,
            val text: String
        )

        data class SeverityVotes(
            val mildVotes: Int,
            val moderateVotes: Int,
            val noneVotes: Int,
            val severeVotes: Int,
            val status: String
        )
    }
}