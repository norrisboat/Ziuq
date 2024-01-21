@file:OptIn(ExperimentalGlideComposeApi::class)

package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.norrisboat.ziuq.android.theme.PrimaryColor
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.dimen

@Composable
fun LiveScoreView(
    modifier: Modifier = Modifier,
    player1ImageURL: String, player1Name: String, player1Score: Int,
    player2ImageURL: String, player2Name: String, player2Score: Int,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(2.dp, color = PrimaryColor)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimen().spacingRegular)
            ) {
                Player1View(
                    modifier = Modifier.weight(1f),
                    imageURL = player1ImageURL,
                    name = player1Name,
                    score = player1Score
                )
                Player2View(
                    modifier = Modifier.weight(1f),
                    imageURL = player2ImageURL,
                    name = player2Name,
                    score = player2Score
                )
            }
            ZiuqText(text = ":", type = ZiuqTextType.Title)

        }
    }
}

@Composable
fun Player1View(
    modifier: Modifier = Modifier,
    imageURL: String,
    name: String,
    score: Int
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GlideImage(
                model = imageURL,
                contentScale = ContentScale.Crop,
                contentDescription = "Player 1 image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            )

            ZiuqText(text = name, type = ZiuqTextType.SmallLabel, maxLines = 1)
        }

        Spacer(modifier = Modifier.weight(1f))

        ScoreCounter(score = score.toString(), style = ZiuqTextType.Title)

    }
}

@Composable
fun Player2View(
    modifier: Modifier = Modifier,
    imageURL: String,
    name: String,
    score: Int
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        ScoreCounter(score = score.toString(), style = ZiuqTextType.Title)

        Spacer(modifier = Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GlideImage(
                model = imageURL,
                contentScale = ContentScale.Crop,
                contentDescription = "Player 2 image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            )

            ZiuqText(text = name, type = ZiuqTextType.SmallLabel, maxLines = 1)
        }

    }
}

@DevicePreviews
@Composable
fun LiveScoreViewPreview() {
    ZiuqTheme {
        LiveScoreView(
            player1ImageURL = "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg",
            player1Name = "Player 1",
            player1Score = 30,
            player2ImageURL = "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg",
            player2Name = "Player 2",
            player2Score = 10,
        )
    }
}