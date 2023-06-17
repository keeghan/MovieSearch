package com.keeghan.movieinfo.models.shows


data class Result(
    val episode: Int, // 314
    val id: String, // /title/tt0944947/
    val image: Image,
    val nextEpisode: String, // /title/tt1480055/
    val numberOfEpisodes: Int, // 73
    val parentTitle: ParentTitle,
    val previousEpisode: String, // /title/tt7545026/
    val principals: List<Principal>,
    val runningTimeInMinutes: Int, // 57
    val season: Int, // 1
    val seriesEndYear: Int, // 2019
    val seriesStartYear: Int, // 2011
    val title: String, // Game of Thrones
    val titleType: String, // tvSeries
    val year: Int // 2011
)