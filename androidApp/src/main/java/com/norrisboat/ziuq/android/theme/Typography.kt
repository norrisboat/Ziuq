package com.norrisboat.ziuq.android.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.norrisboat.ziuq.domain.utils.Fonts
import dev.icerock.moko.resources.compose.asFont

@Composable
private fun urbanist() = FontFamily(
    urbanistRegular()!!,
    urbanistLight()!!,
    urbanistSemiMedium()!!,
    urbanistBold()!!,
    urbanistSemiBold()!!,
    urbanistExtraBold()!!
)

@Composable
fun spaceGrotesk() = FontFamily(
    spaceGroteskRegular()!!,
    spaceGroteskBold()!!,
    spaceGroteskLight()!!,
    spaceGroteskMedium()!!
)

@Composable
fun urbanistRegular(): Font? = Fonts().urbanist.regular.asFont(weight = FontWeight.Normal)

@Composable
fun urbanistLight(): Font? = Fonts().urbanist.light.asFont(weight = FontWeight.Light)

@Composable
fun urbanistBold(): Font? = Fonts().urbanist.bold.asFont(weight = FontWeight.Bold)

@Composable
fun urbanistExtraBold(): Font? = Fonts().urbanist.extraBold.asFont(weight = FontWeight.ExtraBold)

@Composable
fun urbanistSemiBold(): Font? = Fonts().urbanist.semiBold.asFont(weight = FontWeight.SemiBold)

@Composable
fun urbanistSemiMedium(): Font? = Fonts().urbanist.medium.asFont(weight = FontWeight.Medium)

@Composable
fun spaceGroteskRegular(): Font? = Fonts().spaceGrotesk.regular.asFont(weight = FontWeight.Normal)

@Composable
fun spaceGroteskMedium(): Font? = Fonts().spaceGrotesk.medium.asFont(weight = FontWeight.Medium)

@Composable
fun spaceGroteskBold(): Font? = Fonts().spaceGrotesk.bold.asFont(weight = FontWeight.Bold)

@Composable
fun spaceGroteskLight(): Font? = Fonts().spaceGrotesk.light.asFont(weight = FontWeight.Light)

@Composable
fun ziuqTypography() = Typography(
    displayLarge = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.Medium,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.Light,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = spaceGrotesk(),
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = spaceGrotesk(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = spaceGrotesk(),
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = spaceGrotesk(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = spaceGrotesk(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = spaceGrotesk(),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = urbanist(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
