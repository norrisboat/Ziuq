package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.CenterToolBar
import com.norrisboat.ziuq.android.ui.components.ZiuqButton
import com.norrisboat.ziuq.android.ui.components.ZiuqButtonType
import com.norrisboat.ziuq.android.ui.destinations.HomeScreenDestination
import com.norrisboat.ziuq.android.ui.destinations.QuizCompleteScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.android.utils.rememberLifecycleEvent
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.data.ui.QuizUI
import com.norrisboat.ziuq.domain.utils.Labels
import com.norrisboat.ziuq.domain.utils.toUserImage
import com.norrisboat.ziuq.presentation.quiz.QuizViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Destination
@Composable
fun QuizScreen(
    navigator: DestinationsNavigator,
    category: String,
    quizUI: String
) {

    val viewModel = getViewModel<QuizViewModel>()
    val currentQuestion by viewModel.currentQuestion.collectAsStateWithLifecycle()
    val questionNumber by viewModel.questionNumber.collectAsStateWithLifecycle()
    val score by viewModel.currentScore.collectAsStateWithLifecycle()
    val opponentScore by viewModel.opponentScore.collectAsStateWithLifecycle()
    val opponentAnswer by viewModel.opponentAnswer.collectAsStateWithLifecycle()
    val timeLeft by viewModel.timeLeft.collectAsStateWithLifecycle()

    val quiz = QuizUI.decode(quizUI)

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.setQuizUI(quiz)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.completed.collectLatest {
            navigator.navigate(
                QuizCompleteScreenDestination(
                    result = viewModel.quizResult(quiz).encode()
                )
            ) {
                popUpTo(HomeScreenDestination.route) {
                    inclusive = false
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            VerticalProgress(progress = timeLeft, maxProgress = 15f)
            Spacer(modifier = Modifier.weight(1f))
            VerticalProgress(progress = timeLeft, maxProgress = 15f)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimen().spacingSmall)
        ) {

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.CenterEnd) {
                    CenterToolBar(
                        navigator = navigator,
                        backgroundColor = Color.Transparent,
                        title = Labels().categoryQuiz(category).string(),
                        subTitle = Labels().numberQuestion(quiz.questions.count()).string()
                    )

                    if (!quiz.isLiveQuiz()) {
                        QuizScore(
                            modifier = Modifier.padding(end = dimen().spacingMedium),
                            score = score
                        )
                    }
                }

                if (quiz.isLiveQuiz()) {
                    LiveScoreView(
                        modifier = Modifier.padding(
                            start = dimen().spacingRegular,
                            end = dimen().spacingRegular
                        ),
                        player1ImageURL = quiz.player1?.image?.toUserImage() ?: "",
                        player1Name = quiz.player1?.name ?: "",
                        player1Score = score,
                        player2ImageURL = quiz.player2?.image?.toUserImage() ?: "",
                        player2Name = quiz.player2?.name ?: "",
                        player2Score = opponentScore,
                    )
                }

                currentQuestion?.let { currentQuestion ->
                    AnimatedContent(
                        targetState = currentQuestion,
                        transitionSpec = {
                            val animationSpec: TweenSpec<IntOffset> = tween(300)

                            slideInHorizontally(animationSpec = animationSpec) { it } + fadeIn() with
                                    slideOutHorizontally(animationSpec = animationSpec) { -it } + fadeOut()
                        },
                        label = "questionAnimation"
                    ) { question ->
                        QuizQuestionView(
                            modifier = Modifier.padding(
                                start = dimen().spacingMedium,
                                end = dimen().spacingMedium
                            ),
                            questionNumber = questionNumber,
                            question = question,
                            opponentAnswer = opponentAnswer,
                            opponentImage = quiz.player2?.image?.toUserImage() ?: "",
                            isPlayer1 = quiz.player1?.username == viewModel.getUserId()
                        ) { answer ->
                            if (quiz.isLiveQuiz()) {
                                viewModel.submitAnswer(answer)
                            } else {
                                viewModel.nextQuestion(answer)
                            }
                        }
                    }
                }

                if (currentQuestion == null) {
                    CircularProgressIndicator(
                        Modifier.padding(top = dimen().spacingBig),
                        strokeCap = StrokeCap.Round
                    )
                }
            }

            ZiuqButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ZiuqTheme.dimens.spacingMedium),
                text = Labels().skip.string(),
                type = ZiuqButtonType.SecondaryStroke
            ) {
                if (quiz.isLiveQuiz()) {
                    viewModel.submitAnswer()
                } else {
                    viewModel.nextQuestion()
                }
            }
        }

    }
}

@DevicePreviews
@Composable
fun QuizScreenPreview() {
    ZiuqTheme {
        QuizScreen(
            fakeDestination, "Science", QuizUI.sample.encode()
        )
    }
}