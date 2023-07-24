package com.norrisboat.ziuq.android.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.data.ui.QuizQuestion
import com.norrisboat.ziuq.data.ui.QuizUI
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun QuizScreen(
    navigator: DestinationsNavigator,
    quizUI: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopStart
    ) {

    }
}

@DevicePreviews
@Composable
fun QuizScreenPreview() {
    ZiuqTheme {
        val quizUI = QuizUI(
            "", listOf(
                QuizQuestion(
                    "", "What is a bird?",
                    "Animal",
                    listOf("Person", "Object", "Town"),
                    "text"
                )
            )
        )
        QuizScreen(
            fakeDestination, quizUI.encode()
        )
    }
}