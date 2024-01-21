package com.norrisboat.ziuq.android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.utils.DevicePreviews

@Composable
fun ZiuqText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Center,
    type: ZiuqTextType,
    fontWeight: FontWeight? = null,
    textStyle: TextStyle? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = when (type) {
            ZiuqTextType.Heading -> MaterialTheme.typography.displayLarge
            ZiuqTextType.SubHeading -> MaterialTheme.typography.displayMedium
            ZiuqTextType.Title -> MaterialTheme.typography.headlineSmall
            ZiuqTextType.SubTitle -> MaterialTheme.typography.titleMedium
            ZiuqTextType.Label -> MaterialTheme.typography.bodyMedium
            ZiuqTextType.MediumLabel -> MaterialTheme.typography.labelMedium
            ZiuqTextType.SmallLabel -> MaterialTheme.typography.labelSmall
            ZiuqTextType.Custom -> textStyle ?: MaterialTheme.typography.bodyMedium
        },
        textAlign = textAlign,
        fontWeight = fontWeight,
        maxLines = maxLines
    )
}

enum class ZiuqTextType {
    Heading,
    SubHeading,
    Title,
    SubTitle,
    Label,
    MediumLabel,
    SmallLabel,
    Custom
}

@DevicePreviews
@Composable
fun ZiuqTextPreview() {
    ZiuqTheme {
        Column {
            ZiuqText(text = "Click me", type = ZiuqTextType.Heading)
            ZiuqText(text = "Click me", type = ZiuqTextType.SubHeading)
            ZiuqText(text = "Click me", type = ZiuqTextType.Title)
            ZiuqText(text = "Click me", type = ZiuqTextType.SubTitle)
            ZiuqText(text = "Click me", type = ZiuqTextType.Label)
            ZiuqText(
                text = "Click me",
                type = ZiuqTextType.Label,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}