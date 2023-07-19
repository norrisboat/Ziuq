package com.norrisboat.ziuq.android.ui.register

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.CenterToolBar
import com.norrisboat.ziuq.android.ui.components.ZiuqButton
import com.norrisboat.ziuq.android.ui.components.ZiuqButtonType
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextField
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.ui.destinations.HomeScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.MinLengthValidator
import com.norrisboat.ziuq.android.utils.NonEmptyValidator
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.android.utils.showLongToast
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.domain.utils.Labels
import com.norrisboat.ziuq.presentation.register.RegisterScreenState
import com.norrisboat.ziuq.presentation.register.RegisterViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun RegisterScreen(navigator: DestinationsNavigator) {

    val context = LocalContext.current
    val viewModel = getViewModel<RegisterViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isUsernameValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    val isRegisterValid by animateIntAsState(
        targetValue = if (isUsernameValid && isPasswordValid) 1 else 0,
        tween(300), label = "isRegisterValid"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary)
    ) {

        CenterToolBar(navigator = navigator, backgroundColor = Color.Transparent)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GreenPrimary)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
//            val state: RegisterScreenState = RegisterScreenState.Idle
            when (state) {
                is RegisterScreenState.Idle, is RegisterScreenState.Error -> {
                    if (state is RegisterScreenState.Error) {
                        showLongToast(context, (state as RegisterScreenState.Error).errorMessage)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(ZiuqTheme.dimens.spacingBig)
                            .imePadding(),
                        verticalArrangement = Arrangement.spacedBy(
                            ZiuqTheme.dimens.spacingSmall,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ZiuqText(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = Labels().register.string(),
                            type = ZiuqTextType.Heading,
                            color = DeepGreen
                        )
                        ZiuqText(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = Labels().tagline.string(),
                            type = ZiuqTextType.Title,
                            color = DeepGreen
                        )

                        Spacer(modifier = Modifier.height(ZiuqTheme.dimens.spacingMedium))

                        ZiuqTextField(
                            placeholder = Labels().username.string(),
                            validators = listOf(NonEmptyValidator(errorMessage = Labels().usernameEmpty.string()))
                        ) { text, isValid ->
                            isUsernameValid = isValid
                            if (isValid) {
                                username = text
                            }
                        }

                        ZiuqTextField(
                            placeholder = Labels().password.string(),
                            validators = listOf(
                                NonEmptyValidator(errorMessage = Labels().passwordEmpty.string()),
                                MinLengthValidator(minLength = 6)
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        ) { text, isValid ->
                            isPasswordValid = isValid
                            if (isValid) {
                                password = text
                            }
                        }

                        ZiuqButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .scale(if (isRegisterValid == 0) 0.98f else 1f)
                                .padding(top = ZiuqTheme.dimens.spacingSmall),
                            text = Labels().register.string(),
                            type = ZiuqButtonType.Secondary,
                            isEnabled = isRegisterValid == 1
                        ) {
                            viewModel.register(username, password)
                        }

                    }
                }

                is RegisterScreenState.Loading -> {
                    CircularProgressIndicator(
                        Modifier.align(Alignment.Center),
                        strokeCap = StrokeCap.Round
                    )
                }

                is RegisterScreenState.Success -> {
                    navigator.navigate(HomeScreenDestination)
                }
            }
        }

    }
}

@DevicePreviews
@Composable
fun RegisterScreenPreview() {
    ZiuqTheme {
        RegisterScreen(fakeDestination)
    }
}