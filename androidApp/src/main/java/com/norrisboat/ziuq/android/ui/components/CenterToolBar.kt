package com.norrisboat.ziuq.android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    showBackIcon: Boolean = true,
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
            if (showBackIcon) {
                IconButton(
                    onClick = {
                        if (showBackIcon) {
                            navController?.navigateUp()
                            navigator?.navigateUp()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = Labels().backButton.string()
                    )
                }
            }
        },
        title = {
            ZiuqText(
                text = title ?: destination?.topBarTitle(navBackStackEntry) ?: "",
                type = ZiuqTextType.SubTitle
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@DevicePreviews
@Composable
fun CenterToolBarPreview() {
    ZiuqTheme {
        CenterToolBar()
    }
}

@Composable
fun Destination.topBarTitle(navBackStackEntry: NavBackStackEntry?): String {
    return when (this) {
        RegisterScreenDestination -> ""
        else -> javaClass.simpleName.removeSuffix("Destination")
    }
}