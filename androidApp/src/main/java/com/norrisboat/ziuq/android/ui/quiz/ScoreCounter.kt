package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScoreCounter(
    score: Int,
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
        val countString = score.toString()
        val oldCountString = oldScore.toString()
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
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
            score = 0
        )
    }
}