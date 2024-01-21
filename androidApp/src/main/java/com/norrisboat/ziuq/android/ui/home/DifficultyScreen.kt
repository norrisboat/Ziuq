@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.norrisboat.ziuq.android.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.CenterToolBar
import com.norrisboat.ziuq.android.ui.components.DifficultyItem
import com.norrisboat.ziuq.android.ui.destinations.QuizTypeScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.presentation.difficulty.DifficultyScreenState
import com.norrisboat.ziuq.presentation.difficulty.DifficultyViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun DifficultyScreen(navigator: DestinationsNavigator, categoryName: String, categoryKey: String) {

    val viewModel = getViewModel<DifficultyViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary),
    ) {
        CenterToolBar(
            navigator = navigator,
            backgroundColor = Color.Transparent,
            title = categoryName
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (state) {

                    is DifficultyScreenState.Error, is DifficultyScreenState.Idle -> {
                    }

                    is DifficultyScreenState.Loading -> {
                        CircularProgressIndicator(
                            Modifier.padding(top = dimen().spacingBig),
                            strokeCap = StrokeCap.Round
                        )
                    }

                    is DifficultyScreenState.Success -> {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(1f)
                                .padding(dimen().spacingMedium),
                            columns = GridCells.Fixed(2)
                        ) {
                            items((state as DifficultyScreenState.Success).difficulties) { quizDifficulty ->
                                DifficultyItem(
                                    modifier = Modifier.padding(dimen().spacingSmall),
                                    quizDifficulty = quizDifficulty
                                ) {
                                    navigator.navigate(
                                        QuizTypeScreenDestination(
                                            categoryName,
                                            categoryKey,
                                            it.name
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }

    }
}

@DevicePreviews
@Composable
fun DifficultyScreenPreview() {
    ZiuqTheme {
        DifficultyScreen(fakeDestination, "Science", "science")
    }
}