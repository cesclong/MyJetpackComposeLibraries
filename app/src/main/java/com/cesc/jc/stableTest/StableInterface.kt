package com.cesc.jc.stableTest

import android.view.ScaleGestureDetector.OnScaleGestureListener
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember


@Stable
interface UiState<T>{
    val value : T?
    val exception : Throwable?

    val hasError : Boolean
        get() = exception != null
}



@Stable
class NonRestartableComposableTest{
}

@Composable
@NonRestartableComposable
fun rememberNonRestartableComposableTest() = remember {
    NonRestartableComposableTest()
}


@Composable
@NonRestartableComposable
fun EventEffect(event : String, onConsumed: () -> Unit, action : suspend () -> Unit){
    LaunchedEffect(key1 = event, key2 = onConsumed){
        action()
        onConsumed()
    }
}

@Immutable
sealed interface StateEventWithContent<T>

@Immutable
class StateEventWithContentTriggered<T>(val content : T) : StateEventWithContent<T>{
    override fun toString(): String = "triggered($content)"
}

@Immutable
class StateEventWithContentConsumed<T> : StateEventWithContent<T>{
    override fun toString(): String = "consumed"
}

fun <T> triggered(content: T) = StateEventWithContentTriggered(content)



