package com.norrisboat.ziuq.android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.destinations.Destination
import com.norrisboat.ziuq.android.ui.destinations.RegisterScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.domain.utils.Labels
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterial3Api
@Composable
fun CenterToolBar(
    modifier: Modifier = Modifier,
    destination: Destination? = null,
    navBackStackEntry: NavBackStackEntry? = null,
    navController: NavHostController? = null,
    navigator: DestinationsNavigator? = null,
    title: String? = null,
    subTitle: String? = null,
    backgroundColor: Color? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = if (backgroundColor == null) {
            TopAppBarDefaults.centerAlignedTopAppBarColors()
        } else {
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = backgroundColor
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController?.navigateUp()
                    navigator?.navigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = Labels().backButton.string()
                )
            }
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ZiuqText(
                    text = title ?: destination?.topBarTitle(navBackStackEntry) ?: "",
                    type = ZiuqTextType.SubTitle,
                    fontWeight = FontWeight.Bold
                )
                if (!subTitle.isNullOrBlank()) {
                    ZiuqText(
                        text = subTitle,
                        type = ZiuqTextType.SmallLabel,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@DevicePreviews
@Composable
fun CenterToolBarPreview() {
    ZiuqTheme {
        Column {
            CenterToolBar(title = "Dummy title")
            CenterToolBar(title = "Math Quiz", subTitle = "Easy")
        }
    }
}

@Composable
fun Destination.topBarTitle(navBackStackEntry: NavBackStackEntry?): String {
    return when (this) {
        RegisterScreenDestination -> ""
        else -> javaClass.simpleName.removeSuffix("Destination")
    }
}