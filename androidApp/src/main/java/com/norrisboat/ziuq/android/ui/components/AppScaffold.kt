package com.norrisboat.ziuq.android.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.norrisboat.ziuq.android.ui.appCurrentDestinationAsState
import com.norrisboat.ziuq.android.ui.startAppDestination
import com.ramcosta.composedestinations.spec.Route

@OptIn(ExperimentalMaterialNavigationApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit,
) {
    navController.currentBackStack.collectAsState().value.print()

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    Scaffold(content = content)
}

private fun List<NavBackStackEntry>.print(prefix: String = "stack") {
    val stack = map { it.destination.route }.toTypedArray().contentToString()
    println("$prefix = $stack")
}
