package com.keeghan.movieinfo.utils

class Constants {
    companion object {

        const  val apiKey = "05e6730769msh8be126a82e98228p152258jsn6e7ef464b37f"
        const val apiKey2 = "0cfeeaf602msh36e2e39fcdc3c3bp1d1bc6jsnfc4711b1304e"

        const val apiKey3 = "90ef77e7e9msh871b552183aa907p17af6fjsn5b4876d23d66"
        const val host = "online-movie-database.p.rapidapi.com"


        const val CHECK_CAPITAL = "[A-Z]"
        const val CHECK_SYMBOL_DIGIT = "[!@#\\\$%^&*(),.?\\\":{}|<>0-9]"


        const val BASE_URL = "https://imdb8.p.rapidapi.com/title/"

        //PREFS
        const val AUTH_PREFERENCE = "authPreference"
        const val AUTH_TOKEN_KEY = "auth_token"
        const val SIGNIN_KEY = "signed_in"
        const val USER_ID_KEY = "user_Id"
        const val USER_ID_DEFAULT_KEY = 77777777
        const val AUTH_DEFAULT_TOKEN_KEY = "no stored token, you shouldn't be here"


        //values
        const val RETROFIT_READ_TIMEOUT: Long = 30
        const val RETROFIT_WRITE_TIMEOUT: Long = 11
        const val RETROFIT_CONNECT_TIMEOUT: Long = 11

        //Settings
        const val SETTINGS_PREFERENCE = "settingsPreference"
        const val DARK_THEME_KEY = "dark_theme_enabled"


    }
}