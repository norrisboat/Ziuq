package com.norrisboat.ziuq.android.ui.quiz

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.CenterToolBar
import com.norrisboat.ziuq.android.ui.components.ZiuqButton
import com.norrisboat.ziuq.android.ui.components.ZiuqButtonType
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextField
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.ui.destinations.HomeScreenDestination
import com.norrisboat.ziuq.android.ui.destinations.QuizScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.NonEmptyValidator
import com.norrisboat.ziuq.android.utils.collectInLaunchedEffectWithLifecycle
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.android.utils.rememberLifecycleEvent
import com.norrisboat.ziuq.domain.utils.Labels
import com.norrisboat.ziuq.presentation.quiz.LiveQuizScreenNavigation
import com.norrisboat.ziuq.presentation.quiz.LiveQuizScreenState
import com.norrisboat.ziuq.presentation.quiz.LiveQuizViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.resources.compose.localized
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun LiveQuizScreen(
    navigator: DestinationsNavigator,
    categoryName: String,
    categoryKey: String,
    difficulty: String
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val viewModel = getViewModel<LiveQuizViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val liveQuizId by viewModel.liveQuizId.collectAsStateWithLifecycle()

    var quizCode by remember { mutableStateOf("") }

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.setupQuiz(categoryKey, difficulty)
        }
    }

    viewModel.navigation.collectInLaunchedEffectWithLifecycle { navigation ->
        when (navigation) {
            is LiveQuizScreenNavigation.QuizReady -> {
                navigator.navigate(
                    QuizScreenDestination(
                        quizUI = navigation.quizUI.encode(),
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GreenPrimary),
            contentAlignment = Alignment.Center
        ) {

            when (state) {
                is LiveQuizScreenState.Error -> {
                    ZiuqText(
                        text = (state as LiveQuizScreenState.Error).errorMessage,
                        type = ZiuqTextType.Label
                    )
                }

                LiveQuizScreenState.QuizCreated, LiveQuizScreenState.WaitingForOpponent -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimen().spacingRegular),
                        verticalArrangement = Arrangement.spacedBy(dimen().spacingMedium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ZiuqText(
                            text = Labels().quizCode.localized(),
                            type = ZiuqTextType.Title,
                            color = DeepGreen
                        )

                        ScoreCounter(score = liveQuizId, style = ZiuqTextType.Heading)

                        Row(modifier = Modifier.fillMaxWidth()) {
                            ZiuqButton(
                                modifier = Modifier.weight(1f),
                                text = Labels().copyQuizCode.localized(),
                                type = ZiuqButtonType.SecondaryStroke
                            ) {
                                clipboardManager.setText(AnnotatedString(quizCode))
                                Toast.makeText(
                                    context,
                                    Labels().quizCodeCopied.toString(context),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            Spacer(modifier = Modifier.width(dimen().spacingRegular))

                            ZiuqButton(
                                modifier = Modifier.weight(1f),
                                text = Labels().share.localized(),
                                type = ZiuqButtonType.SecondaryStroke
                            ) {
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    putExtra(Intent.EXTRA_TEXT, quizCode)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                context.startActivity(shareIntent)
                            }
                        }

                        if (state is LiveQuizScreenState.WaitingForOpponent) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = dimen().spacingBig),
                                verticalArrangement = Arrangement.spacedBy(dimen().spacingMedium),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeCap = StrokeCap.Round
                                )

                                ZiuqText(
                                    text = Labels().waitingForOpponent.localized(),
                                    type = ZiuqTextType.MediumLabel
                                )
                            }
                        }
                    }
                }

                LiveQuizScreenState.Idle -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimen().spacingRegular),
                        verticalArrangement = Arrangement.spacedBy(dimen().spacingMedium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ZiuqTextField(
                            placeholder = Labels().enterQuizCode.localized(),
                            validators = listOf(NonEmptyValidator(errorMessage = Labels().quizCodeEmpty.localized()))
                        ) { text, _ ->
                            quizCode = text
                        }

                        ZiuqButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .scale(if (quizCode.isNotBlank()) 0.98f else 1f)
                                .padding(
                                    top = ZiuqTheme.dimens.spacingSmall,
                                    bottom = ZiuqTheme.dimens.spacingRegular
                                ),
                            text = Labels().joinQuiz.localized(),
                            type = ZiuqButtonType.Secondary,
                            isEnabled = quizCode.isNotBlank()
                        ) {
                            viewModel.joinQuiz(quizCode)
                        }

                        ZiuqText(text = "/", type = ZiuqTextType.MediumLabel)

                        ZiuqButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .scale(if (quizCode.isNotBlank()) 0.98f else 1f)
                                .padding(top = ZiuqTheme.dimens.spacingRegular),
                            text = Labels().createQuiz.localized(),
                            type = ZiuqButtonType.Secondary
                        ) {
                            viewModel.createQuiz()
                        }
                    }
                }

                LiveQuizScreenState.JoiningQuiz, LiveQuizScreenState.Loading, LiveQuizScreenState.LoadingQuestions, LiveQuizScreenState.CreatingQuiz -> {
                    val loadingText = when (state) {
                        LiveQuizScreenState.JoiningQuiz -> Labels().joiningQuiz.localized()
                        LiveQuizScreenState.Loading -> Labels().loading.localized()
                        LiveQuizScreenState.LoadingQuestions -> Labels().loadingQuestions.localized()
                        LiveQuizScreenState.CreatingQuiz -> Labels().creatingQuizMessage.localized()
                        else -> ""
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimen().spacingRegular),
                        verticalArrangement = Arrangement.spacedBy(dimen().spacingMedium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(strokeCap = StrokeCap.Round)

                        ZiuqText(text = loadingText, type = ZiuqTextType.MediumLabel)
                    }
                }
            }

        }
        CenterToolBar(
            navigator = navigator,
            backgroundColor = Color.Transparent,
            title = ""
        )
    }
}

@DevicePreviews
@Composable
fun LiveQuizScreenPreview() {
    ZiuqTheme {
        LiveQuizScreen(fakeDestination, "", "", "")
    }
}