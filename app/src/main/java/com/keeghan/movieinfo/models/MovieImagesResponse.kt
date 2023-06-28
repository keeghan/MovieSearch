package com.keeghan.movieinfo.models


import com.google.gson.annotations.SerializedName

data class MovieImagesResponse(
    @SerializedName("@type")
    val type: String,
    val images: List<Image>,
    val totalImageCount: Int
) {
    data class Image(
        val caption: String,
        val createdOn: String,
        val height: Int,
        val id: String,
        val url: String,
        val width: Int,
        val relatedNames: List<RelatedName>,
        val relatedTitles: List<RelatedTitle>,
        val source: String,
        val type: String
    ) {
        data class RelatedName(
            val akas: List<String>,
            val disambiguation: String,
            val id: String,
            val image: Image,
            val legacyNameText: String,
            val name: String
        ) {
            data class Image(
                val height: Int,
                val id: String,
                val url: String,
                val width: Int
            )
        }

        data class RelatedTitle(
            val id: String,
            val image: Image,
            val title: String,
            val titleType: String,
            val year: Int,
            val episode: Int,
            val season: Int
        ) {
            data class Image(
                val height: Int,
                val id: String,
                val url: String,
                val width: Int
            )
        }
    }
}