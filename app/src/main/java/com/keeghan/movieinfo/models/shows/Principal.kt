package com.keeghan.movieinfo.models.shows


import com.google.gson.annotations.SerializedName

data class Principal(
    @SerializedName("as")
    val asX: String, // Suk-jin Ji
    val attr: List<String>,
    val billing: Int, // 1
    val category: String, // actress
    val characters: List<String>,
    val disambiguation: String, // II
    val endYear: Int, // 2019
    val episodeCount: Int, // 62
    val id: String, // /name/nm3592338/
    val legacyNameText: String, // Clarke, Emilia
    val name: String, // Emilia Clarke
    val roles: List<Role>,
    val startYear: Int // 2011
)