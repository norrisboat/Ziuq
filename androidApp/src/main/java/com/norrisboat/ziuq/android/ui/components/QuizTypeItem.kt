package com.norrisboat.ziuq.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.LightYellow
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.theme.spaceGrotesk
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.data.ui.QuizType
import com.ramcosta.composedestinations.annotation.Destination
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource


@Destination
@Composable
fun QuizTypeItem(
    modifier: Modifier = Modifier,
    quizType: QuizType,
    onTypeSelected: (QuizType) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(percent = 25))
            .background(LightYellow)
            .border(1.dp, color = Color.White, shape = RoundedCornerShape(percent = 25))
            .clickable { onTypeSelected(quizType) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimen().spacingMedium),
            verticalArrangement = Arrangement.spacedBy(dimen().spacingSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(quizType.imageResource),
                contentDescription = "Category icon",
                colorFilter = ColorFilter.tint(DeepGreen)
            )

            ZiuqText(
                text = quizType.name.localized(),
                type = ZiuqTextType.Custom,
                textStyle = TextStyle(
                    fontFamily = spaceGrotesk(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                ),
                color = DeepGreen
            )

        }

    }
}

@DevicePreviews
@Composable
fun QuizTypeItemPreview() {
    ZiuqTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            QuizTypeItem(quizType = QuizType.startQuiz) {}
        }
    }
}