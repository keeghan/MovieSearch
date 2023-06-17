package com.keeghan.movieinfo.models.shows


import com.google.gson.annotations.SerializedName

data class ShowsResponse(
    @SerializedName("@meta")
    val meta: Meta,
    val query: String, // game of thr
    val results: List<Result>,
    @SerializedName("@type")
    val type: String, // imdb.api.find.response
    val types: List<String>
)