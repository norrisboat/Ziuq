package com.norrisboat.ziuq.android.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.theme.ZiuqShapes
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.Validator
import com.norrisboat.ziuq.android.utils.dimen

@Composable
fun ZiuqTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String = "",
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    validators: List<Validator> = emptyList(),
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String, Boolean) -> Unit = { _, _ -> }
) {

    var text by remember {
        mutableStateOf(value)
    }

    var hasErrors by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(color = Color.White.copy(alpha = 0.5f), shape = ZiuqShapes.small)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
    ) {
        TextField(
            modifier = Modifier
                .padding(
                    start = ZiuqTheme.dimens.spacingSmall,
                    end = ZiuqTheme.dimens.spacingSmall
                )
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (isFocused != it.isFocused) {
                        isFocused = it.isFocused
                    }
                },
            value = text,
            onValueChange = {
                text = it
                if (validators.isNotEmpty()) {
                    var isValid = true
                    for (validator in validators) {
                        if (!validator.validate(it)) {
                            isValid = false
                            error = validator.getError()
                            onValueChange(it, false)
                            break
                        }
                    }
                    hasErrors = !isValid
                    onValueChange(it, isValid)
                } else {
                    onValueChange(it, true)
                }
            },
            label = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    style = if (isFocused) MaterialTheme.typography.labelSmall else MaterialTheme.typography.bodyMedium
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.onBackground,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = hasErrors,
            enabled = enabled,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )

        if (hasErrors) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(
                    start = ZiuqTheme.dimens.spacingMedium,
                    end = ZiuqTheme.dimens.spacingMedium,
                    bottom = dimen().spacingSmall
                )
            )
        }
    }
}

@DevicePreviews
@Composable
fun MyTextFieldPreview() {
    ZiuqTheme {
        ZiuqTextField(
            placeholder = "Type text here"
        )
    }
}