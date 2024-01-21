package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScoreCounter(
    score: String,
    modifier: Modifier = Modifier,
    style: ZiuqTextType = ZiuqTextType.Label
) {
    var oldScore by remember {
        mutableStateOf(score)
    }
    SideEffect {
        oldScore = score
    }
    Row(modifier = modifier) {
        val oldCountString = oldScore
        for (i in score.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = score[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                score[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } with slideOutVertically { -it }
                }, label = "ScoreCounter"
            ) { _ ->
                ZiuqText(text = char.toString(), type = style)
            }
        }
    }
}

@Preview
@Composable
fun ScoreCounterPreview() {
    ZiuqTheme {
        ScoreCounter(
            score = 0.toString()
        )
    }
}