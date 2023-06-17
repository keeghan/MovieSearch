package com.keeghan.movieinfo.models.movieOverview


import com.google.gson.annotations.SerializedName

data class MovieOverViewResponse(
    val certificates: Certificates,
    val genres: List<String>,
    val id: String, // /title/tt0944947/
    val plotOutline: PlotOutline,
    val plotSummary: PlotSummary,
    val ratings: Ratings,
    val releaseDate: String, // 2011-04-17
    val title: Title
) {
    data class Certificates(
        @SerializedName("US")
        val uS: List<US>
    ) {
        data class US(
            val certificate: String, // TV-MA
            val country: String // US
        )
    }

    data class PlotOutline(
        val id: String, // /title/tt0944947/plot/po2596634
        val text: String // Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.
    )

    data class PlotSummary(
        val author: String, // Sam Gray
        val id: String, // /title/tt0944947/plot/ps2733843
        val text: String // In the mythical continent of Westeros, several powerful families fight for control of the Seven Kingdoms. As conflict erupts in the kingdoms of men, an ancient enemy rises once again to threaten them all. Meanwhile, the last heirs of a recently usurped dynasty plot to take back their homeland from across the Narrow Sea.
    )

    data class Ratings(
        val canRate: Boolean, // true
        val otherRanks: List<OtherRank>,
        val rating: Double, // 9.2
        val ratingCount: Int // 1952444
    ) {
        data class OtherRank(
            val id: String, // /chart/ratings/toptv
            val label: String, // Top 250 TV
            val rank: Int, // 13
            val rankType: String // topTv
        )
    }

    data class Title(
        val id: String, // /title/tt0944947/
        val image: Image,
        val nextEpisode: String, // /title/tt1480055/
        val numberOfEpisodes: Int, // 73
        val runningTimeInMinutes: Int, // 57
        val seriesEndYear: Int, // 2019
        val seriesStartYear: Int, // 2011
        val title: String, // Game of Thrones
        val titleType: String, // tvSeries
        @SerializedName("@type")
        val type: String, // imdb.api.title.title
        val year: Int // 2011
    ) {
        data class Image(
            val height: Int, // 1500
            val id: String, // /title/tt0944947/images/rm4204167425
            val url: String, // https://m.media-amazon.com/images/M/MV5BYTRiNDQwYzAtMzVlZS00NTI5LWJjYjUtMzkwNTUzMWMxZTllXkEyXkFqcGdeQXVyNDIzMzcwNjc@._V1_.jpg
            val width: Int // 1102
        )
    }
}