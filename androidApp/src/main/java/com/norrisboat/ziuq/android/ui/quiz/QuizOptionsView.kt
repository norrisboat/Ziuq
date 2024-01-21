@file:OptIn(ExperimentalGlideComposeApi::class)

package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.RedWrong
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuizOptionsView(
    modifier: Modifier = Modifier,
    options: List<String>,
    correctAnswer: String,
    opponentAnswer: String = "",
    opponentImage: String = "",
    isPlayer1: Boolean = true,
    onClick: (String) -> Unit
) {

    var animateResults by remember {
        mutableStateOf(false)
    }

    var correct by remember {
        mutableStateOf("")
    }

    var wrong by remember {
        mutableStateOf("")
    }

    var selectedOption by remember {
        mutableStateOf("")
    }

    LaunchedEffect(animateResults) {
        delay(1.5.seconds)
        if (animateResults) {
            onClick(selectedOption)
            delay(0.5.seconds)
            selectedOption = ""
            correct = ""
            wrong = ""
            animateResults = false
        }
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (animateResults) {
            GreenPrimary
        } else {
            MaterialTheme.colors.background
        },
        animationSpec = tween(300),
        label = "backgroundColor"
    )

    val wrongBackgroundColor by animateColorAsState(
        targetValue = if (animateResults) {
            RedWrong
        } else {
            MaterialTheme.colors.background
        },
        animationSpec = tween(300),
        label = "wrongBackgroundColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (animateResults) {
            DeepGreen
        } else {
            DeepGreen
        },
        animationSpec = tween(300),
        label = "textColor"
    )

    val wrongTextColor by animateColorAsState(
        targetValue = if (animateResults) {
            Color.White
        } else {
            DeepGreen
        },
        animationSpec = tween(300),
        label = "wrongTextColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (animateResults) {
            backgroundColor
        } else {
            Color.LightGray.copy(alpha = 0.7f)
        },
        animationSpec = tween(300),
        label = "borderColor"
    )


    Column(
        modifier = Modifier.padding(
            start = dimen().spacingRegular,
            end = dimen().spacingRegular,
            bottom = dimen().spacingRegular
        ),
        verticalArrangement = Arrangement.spacedBy(dimen().spacingSmall)
    ) {
        options.forEach { option ->
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = if (isPlayer1) Alignment.TopEnd else Alignment.TopStart
            ) {
                QuizOptionButton(
                    modifier = modifier,
                    text = option,
                    backgroundColor = when {
                        correct == option -> backgroundColor
                        wrong == option -> wrongBackgroundColor
                        else -> MaterialTheme.colors.background
                    },
                    borderColor = when {
                        correct == option -> borderColor
                        wrong == option -> wrongBackgroundColor
                        else -> Color.LightGray.copy(alpha = 0.7f)
                    },
                    textColor = when {
                        correct == option -> textColor
                        wrong == option -> wrongTextColor
                        else -> DeepGreen
                    },
                    enabled = selectedOption.isBlank(),
                ) {
                    selectedOption = option
                    if (option.equals(correctAnswer, true)) {
                        correct = option
                    } else {
                        wrong = option
                        correct = correctAnswer
                    }
                    animateResults = true
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = opponentAnswer.isNotBlank() && option.lowercase() == opponentAnswer.lowercase(),
                    label = "opponent",
                    enter = scaleIn(tween(150)),
                    exit = scaleOut(tween(150))
                ) {
                    GlideImage(
                        model = opponentImage,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Player image",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(25.dp)
                    )
                }
            }
        }

    }
}

@DevicePreviews
@Composable
fun QuizOptionsViewPreview() {
    ZiuqTheme {
        QuizOptionsView(
            options = listOf("Person", "Object", "Town", "Animal"),
            correctAnswer = "Person",
            opponentAnswer = "Object"
        ) {}
    }
}