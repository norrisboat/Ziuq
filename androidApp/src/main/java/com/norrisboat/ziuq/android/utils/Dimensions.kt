package com.norrisboat.ziuq.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    val spacingSmall: Dp,
    val spacingMedium: Dp,
    val spacingRegular: Dp,
    val spacingBig: Dp
)

val phoneDimensions = Dimensions(
    spacingSmall = 4.dp,
    spacingMedium = 8.dp,
    spacingRegular = 16.dp,
    spacingBig = 24.dp
)

val tabletDimensions = Dimensions(
    spacingSmall = 8.dp,
    spacingMedium = 16.dp,
    spacingRegular = 24.dp,
    spacingBig = 36.dp
)

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

val LocalAppDimens = staticCompositionLocalOf {
    phoneDimensions
}