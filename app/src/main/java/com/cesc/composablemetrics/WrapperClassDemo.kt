package com.cesc.composablemetrics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier


@Stable
class StableHolder<T>(private val item: T) {
    operator fun component1(): T = item
}

@Immutable
class ImmutableHolder<T>(private val item : T){
    operator fun component1() : T = item
}

@Composable
fun Test(show: StableHolder<String>, modifier: Modifier = Modifier) {
    val (s) = show

}