package com.norrisboat.ziuq.android.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.NavGraphs
import com.norrisboat.ziuq.android.ui.components.ZiuqButton
import com.norrisboat.ziuq.android.ui.components.ZiuqButtonType
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.ui.destinations.LiveQuizScreenDestination
import com.norrisboat.ziuq.android.ui.destinations.LoginScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.norrisboat.ziuq.domain.utils.Labels
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import dev.icerock.moko.resources.compose.localized
import org.koin.compose.koinInject

@Composable
fun MenuSheet(navigator: DestinationsNavigator, onDismissRequest: () -> Unit) {
    val settingsRepository = koinInject<SettingsRepository>()

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp),
            shape = RoundedCornerShape(dimen().spacingMedium),
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(dimen().spacingRegular),
                verticalArrangement = Arrangement.spacedBy(dimen().spacingSmall)
            ) {

                ZiuqText(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = Labels().more.localized(),
                    type = ZiuqTextType.Heading,
                    color = DeepGreen
                )

                ZiuqButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = Labels().joinQuiz.localized()
                ) {
                    navigator.navigate(
                        LiveQuizScreenDestination(
                            categoryKey = "",
                            categoryName = "",
                            difficulty = ""
                        )
                    )
                }

                ZiuqButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = Labels().logout.localized(),
                    type = ZiuqButtonType.Secondary
                ) {
                    settingsRepository.logout()
                    navigator.navigate(LoginScreenDestination) {
                        popUpTo(NavGraphs.root) {
                            inclusive = true
                        }
                    }
                }

            }
        }
    }
}

@DevicePreviews
@Composable
fun MenuSheetPreview() {
    ZiuqTheme {
        MenuSheet(fakeDestination) {}
    }
}