package com.norrisboat.ziuq.android.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.ziuq.android.theme.DeepGreen
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
import dev.icerock.moko.resources.compose.painterResource
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {

    val viewModel = getViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showMenu by remember {
        mutableStateOf(false)
    }

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

            Row(modifier = Modifier.fillMaxWidth()) {
                ZiuqText(
                    text = Labels().hello.string(),
                    type = ZiuqTextType.SubTitle,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            showMenu = true
                        },
                    painter = painterResource(Images().menu),
                    contentDescription = "menu icon",
                    colorFilter = ColorFilter.tint(DeepGreen)
                )
            }

            ZiuqText(
                modifier = Modifier.fillMaxWidth(),
                text = viewModel.getUsername(),
                type = ZiuqTextType.Title,
                textAlign = TextAlign.Start
            )

            when (state) {

                is HomeScreenState.Error, is HomeScreenState.Idle -> {
                    Spacer(modifier = Modifier.weight(1f))
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
                                navigator.navigate(
                                    DifficultyScreenDestination(
                                        categoryName = it.name,
                                        categoryKey = it.key
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showMenu) {
            MenuSheet(navigator = navigator) {
                showMenu = false
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