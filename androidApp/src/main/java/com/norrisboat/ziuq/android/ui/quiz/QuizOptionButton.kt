package com.norrisboat.ziuq.android.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.theme.spaceGrotesk
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.utils.DevicePreviews

@Composable
fun QuizOptionButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    borderColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {

//    LaunchedEffect(isSelected) {
//        delay(1.5.seconds)
//        if (isSelected) {
//            onClick()
//            isSelected = false
//        }
//    }

    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(percent = 100),
        border = BorderStroke(0.5.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        onClick = { onClick() }
    ) {
        ZiuqText(
            text = text,
            type = ZiuqTextType.Custom,
            textStyle = TextStyle(
                fontFamily = spaceGrotesk(),
                fontSize = 14.sp
            ),
            color = textColor
        )
    }
}

@DevicePreviews
@Composable
fun QuizOptionButtonPreview() {
    ZiuqTheme {
        Column {
            QuizOptionButton(
                text = "Sample",
                backgroundColor = MaterialTheme.colors.background,
                borderColor = Color.LightGray.copy(alpha = 0.7f),
                textColor = DeepGreen
            ) {

            }
            QuizOptionButton(
                text = "Sample",
                backgroundColor = MaterialTheme.colors.background,
                borderColor = Color.LightGray.copy(alpha = 0.7f),
                textColor = DeepGreen
            ) {

            }
        }
    }
}