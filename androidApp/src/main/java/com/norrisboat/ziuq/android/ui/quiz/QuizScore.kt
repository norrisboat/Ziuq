package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.utils.DevicePreviews

@Composable
fun QuizScore(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    borderColor: Color = Color.LightGray,
    score: Int
) {

    Box(
        modifier = modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(0.5.dp, borderColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        ScoreCounter(score = score)
    }

}

@DevicePreviews
@Composable
fun QuizScorePreview() {
    ZiuqTheme {
        QuizScore(score = 20)
    }
}