package com.keeghan.movieinfo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


@Composable
fun rememberStarRate(fillColor: Color): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "star_rate",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(fillColor),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 28.625f)
                lineToRelative(-6.75f, 5.125f)
                quadToRelative(-0.375f, 0.333f, -0.792f, 0.292f)
                quadToRelative(-0.416f, -0.042f, -0.75f, -0.292f)
                quadToRelative(-0.333f, -0.208f, -0.5f, -0.583f)
                quadToRelative(-0.166f, -0.375f, 0f, -0.875f)
                lineToRelative(2.584f, -8.334f)
                lineToRelative(-6.625f, -4.791f)
                quadToRelative(-0.417f, -0.25f, -0.521f, -0.667f)
                quadToRelative(-0.104f, -0.417f, 0.021f, -0.792f)
                quadToRelative(0.125f, -0.375f, 0.437f, -0.646f)
                quadToRelative(0.313f, -0.27f, 0.813f, -0.27f)
                horizontalLineToRelative(8.208f)
                lineToRelative(2.625f, -8.709f)
                quadToRelative(0.125f, -0.5f, 0.5f, -0.729f)
                quadToRelative(0.375f, -0.229f, 0.75f, -0.229f)
                reflectiveQuadToRelative(0.75f, 0.229f)
                quadToRelative(0.375f, 0.229f, 0.5f, 0.729f)
                lineToRelative(2.625f, 8.709f)
                horizontalLineToRelative(8.208f)
                quadToRelative(0.5f, 0f, 0.813f, 0.27f)
                quadToRelative(0.312f, 0.271f, 0.437f, 0.646f)
                reflectiveQuadToRelative(0.021f, 0.792f)
                quadToRelative(-0.104f, 0.417f, -0.521f, 0.667f)
                lineToRelative(-6.625f, 4.791f)
                lineToRelative(2.584f, 8.334f)
                quadToRelative(0.166f, 0.5f, 0f, 0.875f)
                quadToRelative(-0.167f, 0.375f, -0.5f, 0.583f)
                quadToRelative(-0.334f, 0.25f, -0.75f, 0.292f)
                quadToRelative(-0.417f, 0.041f, -0.792f, -0.292f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberStarRateEmpty(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "star_rate",
            defaultWidth = 50.0.dp,
            defaultHeight = 50.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(15.5f, 28.375f)
                lineToRelative(4.5f, -3.458f)
                lineToRelative(4.5f, 3.458f)
                lineToRelative(-1.708f, -5.708f)
                lineTo(27f, 19.792f)
                horizontalLineToRelative(-5.167f)
                lineTo(20f, 14f)
                lineToRelative(-1.833f, 5.792f)
                horizontalLineToRelative(-5.125f)
                lineToRelative(4.208f, 2.875f)
                close()
                moveToRelative(4.5f, -1.667f)
                lineToRelative(-5.292f, 4.042f)
                quadToRelative(-0.208f, 0.125f, -0.437f, 0.104f)
                quadToRelative(-0.229f, -0.021f, -0.396f, -0.146f)
                quadToRelative(-0.208f, -0.083f, -0.292f, -0.291f)
                quadToRelative(-0.083f, -0.209f, 0f, -0.459f)
                lineToRelative(2f, -6.625f)
                lineToRelative(-5.041f, -3.666f)
                quadToRelative(-0.209f, -0.167f, -0.292f, -0.375f)
                quadToRelative(-0.083f, -0.209f, 0f, -0.417f)
                reflectiveQuadToRelative(0.25f, -0.354f)
                quadToRelative(0.167f, -0.146f, 0.417f, -0.146f)
                horizontalLineToRelative(6.375f)
                lineToRelative(2.041f, -6.792f)
                quadToRelative(0.084f, -0.25f, 0.271f, -0.395f)
                quadToRelative(0.188f, -0.146f, 0.396f, -0.146f)
                quadToRelative(0.208f, 0f, 0.396f, 0.146f)
                quadToRelative(0.187f, 0.145f, 0.271f, 0.395f)
                lineToRelative(2.041f, 6.792f)
                horizontalLineToRelative(6.417f)
                quadToRelative(0.208f, 0f, 0.375f, 0.146f)
                reflectiveQuadToRelative(0.25f, 0.354f)
                quadToRelative(0.083f, 0.208f, 0f, 0.417f)
                quadToRelative(-0.083f, 0.208f, -0.25f, 0.375f)
                lineToRelative(-5.083f, 3.666f)
                lineToRelative(2f, 6.625f)
                quadToRelative(0.083f, 0.25f, 0f, 0.459f)
                quadToRelative(-0.084f, 0.208f, -0.25f, 0.291f)
                quadToRelative(-0.209f, 0.125f, -0.438f, 0.146f)
                quadToRelative(-0.229f, 0.021f, -0.396f, -0.104f)
                close()
                moveToRelative(0f, -5.5f)
                close()
            }
        }.build()
    }
}