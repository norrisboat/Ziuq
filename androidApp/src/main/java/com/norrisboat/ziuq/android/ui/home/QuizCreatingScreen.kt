package com.norrisboat.ziuq.android.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.CenterToolBar
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.ui.destinations.HomeScreenDestination
import com.norrisboat.ziuq.android.ui.destinations.QuizScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.android.utils.rememberLifecycleEvent
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.data.remote.request.CreateQuizRequest
import com.norrisboat.ziuq.domain.utils.Labels
import com.norrisboat.ziuq.presentation.quiz.CreateQuizScreenState
import com.norrisboat.ziuq.presentation.quiz.CreateQuizViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun QuizCreatingScreen(
    navigator: DestinationsNavigator,
    categoryName: String,
    categoryKey: String,
    difficulty: String
) {
    val viewModel = getViewModel<CreateQuizViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.createQuiz(
                CreateQuizRequest(
                    categoryKey,
                    difficulty.lowercase(),
                    "text_choice"
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary),
        contentAlignment = Alignment.TopStart
    ) {
        CenterToolBar(
            navigator = navigator,
            backgroundColor = Color.Transparent,
            title = String.format("%s - %s", categoryName, difficulty)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

//            val state: CreateQuizScreenState = CreateQuizScreenState.Loading
            when (state) {

                is CreateQuizScreenState.Error -> {
                }

                is CreateQuizScreenState.Loading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            dimen().spacingRegular
                        )
                    ) {
                        CircularProgressIndicator(
                            Modifier.padding(top = dimen().spacingBig),
                            strokeCap = StrokeCap.Round
                        )
                        ZiuqText(
                            text = Labels().creatingQuiz.string(),
                            type = ZiuqTextType.MediumLabel
                        )
                    }
                }

                is CreateQuizScreenState.Success -> {
                    navigator.navigate(
                        QuizScreenDestination(
                            quizUI = (state as CreateQuizScreenState.Success).quizUI.encode(),
                            category = categoryName
                        )
                    ) {
                        popUpTo(HomeScreenDestination.route) {
                            inclusive = false
                        }
                    }
                }
            }
        }

    }

}


@DevicePreviews
@Composable
fun QuizCreatingScreenPreview() {
    ZiuqTheme {
        QuizCreatingScreen(fakeDestination, "Science", "science", "Easy")
    }
}