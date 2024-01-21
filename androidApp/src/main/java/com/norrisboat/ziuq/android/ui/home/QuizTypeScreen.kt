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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.CenterToolBar
import com.norrisboat.ziuq.android.ui.components.QuizTypeItem
import com.norrisboat.ziuq.android.ui.destinations.LiveQuizScreenDestination
import com.norrisboat.ziuq.android.ui.destinations.QuizCreatingScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.data.ui.QuizType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun QuizTypeScreen(
    navigator: DestinationsNavigator,
    categoryName: String,
    categoryKey: String,
    difficulty: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenterToolBar(
                navigator = navigator,
                backgroundColor = Color.Transparent,
                title = ""
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .padding(dimen().spacingMedium),
                columns = GridCells.Fixed(2)
            ) {
                items(listOf(QuizType.multiplayer, QuizType.startQuiz)) { quizType ->
                    QuizTypeItem(
                        modifier = Modifier.padding(dimen().spacingSmall),
                        quizType = quizType
                    ) {
                        if (quizType == QuizType.startQuiz) {
                            navigator.navigate(
                                QuizCreatingScreenDestination(
                                    categoryName,
                                    categoryKey,
                                    difficulty
                                )
                            )
                        } else {
                            navigator.navigate(
                                LiveQuizScreenDestination(
                                    categoryName,
                                    categoryKey,
                                    difficulty
                                )
                            )
                        }
                    }
                }
            }

        }

    }
}

@DevicePreviews
@Composable
fun QuizTypeScreenPreview() {
    ZiuqTheme {
        QuizTypeScreen(fakeDestination, "Science", "science", "")
    }
}