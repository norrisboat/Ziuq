package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.RedWrong
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.utils.DevicePreviews

@Composable
fun VerticalProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    maxProgress: Float = 100f
) {
    val animProgress by animateFloatAsState(
        targetValue = progress / maxProgress,
        tween(1000),
        label = "progress"
    )
    Column(
        modifier = modifier
            .background(Color.LightGray.copy(alpha = 0.3f))
            .width(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight((1 - animProgress).coerceAtLeast(0.001f))
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .weight(animProgress.coerceAtLeast(0.001f))
                .fillMaxWidth()
                .background(if (animProgress > 0.2) DeepGreen else RedWrong)
        )
    }
}

@DevicePreviews
@Composable
fun VerticalProgressPreview() {
    ZiuqTheme {
        VerticalProgress(progress = 50f)
    }
}