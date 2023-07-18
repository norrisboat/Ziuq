package com.norrisboat.ziuq.android.ui.login

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norrisboat.ziuq.android.theme.DeepGreen
import com.norrisboat.ziuq.android.theme.GreenPrimary
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.norrisboat.ziuq.android.ui.components.ZiuqButton
import com.norrisboat.ziuq.android.ui.components.ZiuqButtonType
import com.norrisboat.ziuq.android.ui.components.ZiuqText
import com.norrisboat.ziuq.android.ui.components.ZiuqTextField
import com.norrisboat.ziuq.android.ui.components.ZiuqTextType
import com.norrisboat.ziuq.android.ui.destinations.HomeScreenDestination
import com.norrisboat.ziuq.android.ui.destinations.RegisterScreenDestination
import com.norrisboat.ziuq.android.utils.DevicePreviews
import com.norrisboat.ziuq.android.utils.NonEmptyValidator
import com.norrisboat.ziuq.android.utils.fakeDestination
import com.norrisboat.ziuq.android.utils.showLongToast
import com.norrisboat.ziuq.android.utils.string
import com.norrisboat.ziuq.domain.utils.Labels
import com.norrisboat.ziuq.presentation.login.LoginScreenState
import com.norrisboat.ziuq.presentation.login.LoginViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(navigator: DestinationsNavigator) {

    val context = LocalContext.current
    val viewModel = getViewModel<LoginViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoginIdValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    val isLoginValid by animateIntAsState(
        targetValue = if (isLoginIdValid && isPasswordValid) 1 else 0,
        tween(300), label = "isLoginValid"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary)
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
//        val state: LoginScreenState = LoginScreenState.Idle
        when (state) {
            is LoginScreenState.Idle, is LoginScreenState.Error -> {
                if (state is LoginScreenState.Error) {
                    showLongToast(context, (state as LoginScreenState.Error).errorMessage)
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    ZiuqText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = ZiuqTheme.dimens.spacingRegular,top = ZiuqTheme.dimens.spacingBig)
                            .clickable { viewModel.openRegister() },
                        textAlign = TextAlign.End,
                        text = Labels().noAccountRegister.string(),
                        type = ZiuqTextType.Title,
                        color = DeepGreen
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(ZiuqTheme.dimens.spacingBig),
                        verticalArrangement = Arrangement.spacedBy(
                            ZiuqTheme.dimens.spacingSmall,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ZiuqText(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = Labels().app.string(),
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
                            isLoginIdValid = isValid
                            if (isValid) {
                                username = text
                            }
                        }

                        ZiuqTextField(
                            placeholder = Labels().password.string(),
                            validators = listOf(NonEmptyValidator(errorMessage = Labels().passwordEmpty.string())),
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
                                .scale(if (isLoginValid == 0) 0.98f else 1f)
                                .padding(top = ZiuqTheme.dimens.spacingSmall),
                            text = Labels().login.string(),
                            type = ZiuqButtonType.Secondary,
                            isEnabled = isLoginValid == 1
                        ) {
                            viewModel.login(username, password)
                        }

                    }
                }
            }

            is LoginScreenState.Loading -> {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center),
                    strokeCap = StrokeCap.Round
                )
            }

            is LoginScreenState.Register -> {
                navigator.navigate(RegisterScreenDestination)
            }

            is LoginScreenState.Success -> {
                navigator.navigate(HomeScreenDestination)
            }
        }
    }
}

@DevicePreviews
@Composable
fun LoginScreenPreview() {
    ZiuqTheme {
        LoginScreen(navigator = fakeDestination)
    }
}