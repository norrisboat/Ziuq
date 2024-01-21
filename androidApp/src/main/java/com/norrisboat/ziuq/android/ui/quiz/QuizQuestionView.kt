package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.theme.spaceGrotesk
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.data.ui.QuizQuestion
import com.norrisboat.ziuq.domain.utils.Labels

@Composable
fun QuizQuestionView(
    modifier: Modifier = Modifier,
    questionNumber: Int,
    question: QuizQuestion,
    opponentAnswer: String = "",
    opponentImage: String = "",
    isPlayer1: Boolean = true,
    onAnswer: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimen().spacingMedium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colors.background,
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        ZiuqText(
            modifier = Modifier.padding(
                start = dimen().spacingRegular,
                bottom = dimen().spacingSmall,
                top = dimen().spacingSmall
            ),
            text = Labels().questionNumber(questionNumber).string(),
            type = ZiuqTextType.Custom,
            textStyle = TextStyle(
                fontFamily = spaceGrotesk(),
                fontSize = 16.sp
            ),
        )
        ZiuqText(
            modifier = Modifier.padding(
                start = dimen().spacingRegular,
                end = dimen().spacingRegular,
                bottom = dimen().spacingRegular
            ),
            text = question.question,
            type = ZiuqTextType.Custom,
            textStyle = TextStyle(
                fontFamily = spaceGrotesk(),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            color = DeepGreen,
            textAlign = TextAlign.Start
        )

        QuizOptionsView(
            modifier = modifier,
            options = question.options,
            correctAnswer = question.correctAnswer,
            opponentAnswer = opponentAnswer,
            opponentImage = opponentImage,
            isPlayer1 = isPlayer1,
        ) { answer ->
            onAnswer(answer)
        }
    }
}

@DevicePreviews
@Composable
fun QuizQuestionViewPreview() {
    ZiuqTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            QuizQuestionView(
                questionNumber = 1,
                question = QuizQuestion.sample
            ) {}
        }
    }
}