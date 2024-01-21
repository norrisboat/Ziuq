package com.norrisboat.ziuq.android.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import com.norrisboat.ziuq.android.theme.ZiuqTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

val fakeDestination = object : DestinationsNavigator {
    override fun clearBackStack(route: String): Boolean {
        return true
    }

    override fun navigate(
        route: String,
        onlyIfResumed: Boolean,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {

    }

    override fun navigate(
        route: String,
        onlyIfResumed: Boolean,
        builder: NavOptionsBuilder.() -> Unit
    ) {
    }

    override fun navigateUp(): Boolean {
        return true
    }

    override fun popBackStack(): Boolean {
        return true
    }

    override fun popBackStack(
        route: String,
        inclusive: Boolean,
        saveState: Boolean
    ): Boolean {
        return true
    }

}

@Composable
fun StringDesc?.string() = this?.toString(context()) ?: ""

@Composable
fun context() = LocalContext.current

@Composable
fun dimen() = ZiuqTheme.dimens

fun showLongToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}

@Suppress("ComposableNaming")
@Composable
fun <T> Flow<T>.collectInLaunchedEffectWithLifecycle(
    vararg keys: Any?,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend CoroutineScope.(T) -> Unit
) {
    val flow = this
    val currentCollector by rememberUpdatedState(collector)

    LaunchedEffect(flow, lifecycle, minActiveState, *keys) {
        withContext(Dispatchers.Main.immediate) {
            lifecycle.repeatOnLifecycle(minActiveState) {
                flow.collect { currentCollector(it) }
            }
        }
    }
}