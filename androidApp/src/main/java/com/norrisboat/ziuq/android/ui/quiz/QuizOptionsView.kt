package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.RedWrong
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun QuizOptionsView(
    modifier: Modifier = Modifier,
    options: List<String>,
    correctAnswer: String,
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
            correct = ""
            wrong = ""
            selectedOption = ""
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
                }
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
        }

    }
}

@DevicePreviews
@Composable
fun QuizOptionsViewPreview() {
    ZiuqTheme {
        QuizOptionsView(
            options = listOf("Person", "Object", "Town", "Animal"),
            correctAnswer = "Person"
        ) {}
    }
}