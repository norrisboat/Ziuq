package com.norrisboat.ziuq.android.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqShapes
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.utils.DevicePreviews


@Composable
fun ZiuqButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ZiuqButtonType = ZiuqButtonType.Primary,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {

    ElevatedButton(
        modifier = modifier
            .defaultMinSize(minHeight = 64.dp)
            .padding(ZiuqTheme.dimens.spacingSmall),
        onClick = onClick,
        shape = ZiuqShapes.small,
        border = BorderStroke(
            when (type) {
                ZiuqButtonType.PrimaryStroke, ZiuqButtonType.SecondaryStroke -> 2.dp
                else -> 0.dp
            },
            when (type) {
                ZiuqButtonType.PrimaryStroke -> GreenPrimary
                ZiuqButtonType.SecondaryStroke -> DeepGreen
                else -> Color.Transparent
            },
        ),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = when (type) {
                ZiuqButtonType.Primary -> GreenPrimary
                ZiuqButtonType.Secondary -> DeepGreen
                ZiuqButtonType.PrimaryStroke, ZiuqButtonType.SecondaryStroke -> Color.Transparent
            },
            disabledContainerColor = when (type) {
                ZiuqButtonType.Primary -> GreenPrimary.copy(alpha = 0.5f)
                ZiuqButtonType.Secondary -> DeepGreen.copy(alpha = 0.5f)
                ZiuqButtonType.PrimaryStroke, ZiuqButtonType.SecondaryStroke -> Color.Transparent
            },
            contentColor = when (type) {
                ZiuqButtonType.Primary -> DeepGreen
                ZiuqButtonType.PrimaryStroke -> GreenPrimary
                ZiuqButtonType.Secondary -> Color.White
                ZiuqButtonType.SecondaryStroke -> DeepGreen
            },
            disabledContentColor = when (type) {
                ZiuqButtonType.Primary, ZiuqButtonType.SecondaryStroke -> DeepGreen.copy(alpha = 0.5f)
                ZiuqButtonType.PrimaryStroke -> GreenPrimary.copy(alpha = 0.5f)
                ZiuqButtonType.Secondary -> Color.White
            }
        ),
        enabled = isEnabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }

}

enum class ZiuqButtonType {
    Primary,
    PrimaryStroke,
    Secondary,
    SecondaryStroke,
}

@DevicePreviews
@Composable
fun ZiuqButtonPreview() {
    ZiuqTheme {
        Column {
            ZiuqButtonType.values().forEach { type ->
                ZiuqButton(text = "Click me", type = type) {

                }
            }
        }
    }
}