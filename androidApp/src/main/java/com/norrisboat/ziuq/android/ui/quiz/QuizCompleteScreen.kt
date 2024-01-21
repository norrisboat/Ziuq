package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.ScoreTower
import com.norrisboat.ziuq.android.ui.components.ZiuqButton
import com.norrisboat.ziuq.android.ui.components.ZiuqButtonType
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.data.ui.QuizResult
import com.norrisboat.ziuq.domain.utils.Labels
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.resources.compose.localized

@Destination
@Composable
fun QuizCompleteScreen(navigator: DestinationsNavigator, result: String) {

    val quizResult by remember {
        mutableStateOf(QuizResult.decode(result))
    }

    val p1Color by remember {
        mutableStateOf(
            when {
                quizResult.p1Score == quizResult.p2Score -> Color.LightGray
                quizResult.p1Score > quizResult.p2Score -> Color.Green
                else -> Color.Red
            }
        )
    }

    val p2Color by remember {
        mutableStateOf(
            when (p1Color) {
                Color.Red -> {
                    Color.Green
                }

                Color.LightGray -> {
                    Color.LightGray
                }

                else -> {
                    Color.Red
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary)
            .padding(vertical = dimen().spacingBig)
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZiuqText(
                text = when {
                    !quizResult.isLiveQuiz -> Labels().congratulations.localized()
                    quizResult.p1Score == quizResult.p2Score -> Labels().draw.localized()
                    quizResult.p1Score > quizResult.p2Score -> Labels().youWin.localized()
                    else -> Labels().youLost.localized()
                }, type = ZiuqTextType.SubHeading
            )
            ZiuqText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimen().spacingMedium),
                text = Labels().congratulationsMessage.string(),
                type = ZiuqTextType.SubTitle
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = dimen().spacingRegular),
            horizontalArrangement = Arrangement.Center
        ) {
            if (quizResult.isLiveQuiz) {
                ScoreTower(
                    modifier = Modifier.padding(top = 80.dp),
                    progress = quizResult.p1Score,
                    maxProgress = 100,
                    name = quizResult.p1Name,
                    image = quizResult.p1Image,
                    strokeColor = p1Color
                )
                Spacer(modifier = Modifier.width(dimen().spacingMedium))
                ScoreTower(
                    modifier = Modifier.padding(top = 80.dp),
                    progress = quizResult.p2Score,
                    maxProgress = 100,
                    name = quizResult.p2Name,
                    image = quizResult.p2Image,
                    strokeColor = p2Color
                )
            } else {
                ScoreTower(
                    progress = quizResult.p1Score,
                    maxProgress = 100,
                    name = quizResult.p1Name,
                    image = quizResult.p1Image,
                    strokeColor = Color.Transparent
                )
            }
        }

        ZiuqButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ZiuqTheme.dimens.spacingSmall),
            text = Labels().done.string(),
            type = ZiuqButtonType.Secondary
        ) {
            navigator.navigateUp()
        }

    }
}

@Preview
@Composable
fun QuizCompleteScreenPreview() {
    ZiuqTheme {
        QuizCompleteScreen(fakeDestination, QuizResult.sample.encode())
    }
}