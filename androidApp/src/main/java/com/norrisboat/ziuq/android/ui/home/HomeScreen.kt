package com.norrisboat.ziuq.android.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.CategoryItem
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.ui.destinations.DifficultyScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.data.ui.QuizCategory
import com.norrisboat.ziuq.domain.utils.Images
import com.norrisboat.ziuq.domain.utils.Labels
import com.norrisboat.ziuq.presentation.home.HomeScreenState
import com.norrisboat.ziuq.presentation.home.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {

    val viewModel = getViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val list = listOf(
        QuizCategory("Science", "science", Images().science),
        QuizCategory("Food And Drinks", "science", Images().food),
        QuizCategory("Music", "science", Images().music)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(dimen().spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZiuqText(
                modifier = Modifier.fillMaxWidth(),
                text = Labels().hello.string(),
                type = ZiuqTextType.MediumLabel,
                textAlign = TextAlign.Start
            )
            ZiuqText(
                modifier = Modifier.fillMaxWidth(),
                text = Labels().dearUser.string(),
                type = ZiuqTextType.Title,
                textAlign = TextAlign.Start
            )

            when (state) {

                is HomeScreenState.Error, is HomeScreenState.Idle -> {
                }

                is HomeScreenState.Loading -> {
                    CircularProgressIndicator(
                        Modifier.padding(top = dimen().spacingBig),
                        strokeCap = StrokeCap.Round
                    )
                }

                is HomeScreenState.Success -> {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(1f)
                            .padding(top = dimen().spacingRegular),
                        columns = GridCells.Fixed(2)
                    ) {
                        items((state as HomeScreenState.Success).categories) { quizCategory ->
                            CategoryItem(
                                modifier = Modifier.padding(dimen().spacingSmall),
                                quizCategory = quizCategory
                            ) {
                                navigator.navigate(DifficultyScreenDestination(categoryName = it.name, categoryKey = it.key))
                            }
                        }
                    }
                }
            }
        }

    }
}

@DevicePreviews
@Composable
fun HomeScreenPreview() {
    ZiuqTheme {
        HomeScreen(fakeDestination)
    }
}