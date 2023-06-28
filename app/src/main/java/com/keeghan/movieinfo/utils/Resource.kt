package com.keeghan.movieinfo.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

@Composable
fun SmallSpaceH(){
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun SmallSpaceW(){
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
fun MediumHSpace(){
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun largeSpace(){
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SpaceH(side:Dp){
    Spacer(modifier = Modifier.height(side))
}

@Composable
fun SpaceW(side:Dp){
    Spacer(modifier = Modifier.width(side))
}