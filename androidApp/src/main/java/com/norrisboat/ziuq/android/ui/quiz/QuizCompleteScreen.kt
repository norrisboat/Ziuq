package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.ZiuqButton
import com.norrisboat.ziuq.android.ui.components.ZiuqButtonType
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.domain.utils.Labels
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun QuizCompleteScreen(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary)
            .padding(dimen().spacingBig)
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            ZiuqText(text = Labels().congratulations.string(), type = ZiuqTextType.SubHeading)
            ZiuqText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimen().spacingMedium),
                text = Labels().congratulationsMessage.string(),
                type = ZiuqTextType.SubTitle
            )
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
        QuizCompleteScreen(fakeDestination)
    }
}