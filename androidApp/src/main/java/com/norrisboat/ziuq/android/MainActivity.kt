package com.norrisboat.ziuq.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.NavGraphs
import com.norrisboat.ziuq.android.ui.components.AppScaffold
import com.norrisboat.ziuq.android.ui.destinations.HomeScreenDestination
import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import org.koin.android.ext.android.inject

@OptIn(ExperimentalMaterialNavigationApi::class)
class MainActivity : ComponentActivity() {

    private val settingsRepository: SettingsRepository by inject()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZiuqTheme {

                val engine = rememberAnimatedNavHostEngine()
                val navController = engine.rememberNavController()
                val startRoute =
                    if (settingsRepository.isLoggedIn()) HomeScreenDestination else NavGraphs.root.startRoute

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    AppScaffold(
                        navController = navController,
                        startRoute = startRoute,
                        topBar = { _, _ ->
//                            if (dest.shouldShowScaffoldElements) {
//                                CenterToolBar(
//                                    destination = dest,
//                                    navBackStackEntry = backStackEntry,
//                                    navController = navController,
//                                    showBackIcon = dest.showBackButton,
//                                    showSettings = dest.showSettings
//                                )
//                            }
                        },
                        bottomBar = {
//                            if (it.shouldShowScaffoldElements) {
//                                BottomBar(navController)
//                            }
                        }
                    ) {

                        DestinationsNavHost(
                            engine = engine,
                            navController = navController,
                            navGraph = NavGraphs.root,
                            modifier = Modifier.padding(),
                            startRoute = startRoute
                        )
                    }
                }
            }
        }
    }
}
