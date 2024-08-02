package com.keeghan.movieinfo.utils

import androidx.compose.ui.unit.dp

object Constants {
    const val apiKey1 = "05e6730769msh8be126a82e98228p152258jsn6e7ef464b37f"
    const val apiKey2 = "0cfeeaf602msh36e2e39fcdc3c3bp1d1bc6jsnfc4711b1304e"
    const val apikey_1_2_Host = "imdb8.p.rapidapi.com"

    const val APIKEY = "90ef77e7e9msh871b552183aa907p17af6fjsn5b4876d23d66"
    const val HOST = "online-movie-database.p.rapidapi.com"



    const val BASE_URL = "https://imdb8.p.rapidapi.com/title/"

    const val APP_VERSION = "8.8.7.02"
    val BOTTOM_BAR_PADDING = 55.dp

    //values
    const val RETROFIT_READ_TIMEOUT: Long = 30
    const val RETROFIT_WRITE_TIMEOUT: Long = 11
    const val RETROFIT_CONNECT_TIMEOUT: Long = 11


    object ThemeSettings{
        const val DARK_THEME = 0
        const val LIGHT_THEME = 1
    }

    object RegionSettings {
        const val UK = 0
        const val US = 1
    }

    object NetworkSettings {
        const val WIFI_CELLULAR = 0
        const val WIFI_ONLY = 1
        const val NO_AUTO_PLAY = 2
    }

    object SubTitleOptions {
        const val ALWAYS_ON = 0
        const val ON_WHEN_MUTED = 1
        const val OFF = 2
    }

    object StreamingOptions {
        const val COM = 0
        const val CO_UK = 1
        const val DO = 2
        const val IN = 3
    }

}