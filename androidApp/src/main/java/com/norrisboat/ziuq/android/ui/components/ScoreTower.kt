package com.norrisboat.ziuq.android.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.utils.DevicePreviews
import kotlinx.coroutines.delay

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ScoreTower(
    modifier: Modifier = Modifier,
    progress: Int,
    maxProgress: Int,
    name: String,
    image: String,
    strokeColor: Color
) {
    val brush = Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.8f), Color.Transparent))

    var tempProgress by rememberSaveable {
        mutableStateOf(0)
    }

    val animProgress by animateFloatAsState(
        targetValue = (tempProgress) / (maxProgress + 50).toFloat(),
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = StiffnessVeryLow

        ),
        label = "progress"
    )

    val animHeight by animateDpAsState(targetValue = 160.dp, tween(2000), label = "animHeight")

    LaunchedEffect(key1 = Unit, block = {
        delay(500L)
        tempProgress = progress + 50
    })

    Column(
        modifier = modifier
            .width(animHeight)
            .fillMaxHeight()
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
                .background(brush = brush),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .size(115.dp)
                    .offset(y = -(55).dp)
                    .rotate(45f)
                    .background(DeepGreen)
            )
//            Box(
//                modifier = Modifier
//                    .size(80.dp)
//                    .offset(y = -(95).dp)
//                    .clip(CircleShape)
//                    .border(2.dp, strokeColor, CircleShape)
//                    .background(Color.Blue)
//            )
            ZiuqText(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 90.dp),
                text = name,
                type = ZiuqTextType.Label,
                color = DeepGreen
            )
            ZiuqText(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 120.dp),
                text = progress.toString(),
                type = ZiuqTextType.Title,
                color = DeepGreen
            )
            if (image.isNotBlank()) {
                GlideImage(
                    model = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Player image",
                    modifier = Modifier
                        .size(80.dp)
                        .offset(y = -(95).dp)
                        .clip(CircleShape)
                        .border(2.dp, strokeColor, CircleShape),
                )
            }
        }
    }
}

@DevicePreviews
@Composable
fun ScoreTowerPreview() {
    ZiuqTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GreenPrimary)
                .padding(24.dp)
        ) {
            Row {
                ScoreTower(
                    progress = 50,
                    maxProgress = 100,
                    name = "Player 1",
                    image = "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg",
                    strokeColor = Color.Red
                )
                Spacer(modifier = Modifier.width(20.dp))
                ScoreTower(
                    progress = 80,
                    maxProgress = 100,
                    name = "Player 1",
                    image = "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg",
                    strokeColor = Color.Green
                )
            }
        }
    }
}