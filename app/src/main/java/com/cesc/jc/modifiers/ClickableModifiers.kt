package com.cesc.jc.modifiers

import android.os.SystemClock
import android.system.SystemCleaner
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

const val MIN_DURATION = 500L

inline fun Modifier.clickableLimit(
    minDuration: Long = MIN_DURATION, crossinline onClick: () -> Unit
): Modifier = composed {
    clickableLimit(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        minDuration = minDuration,
        onClick = onClick
    )
}


inline fun Modifier.clickableLimit(
    indication: Indication?,
    interactionSource: MutableInteractionSource,
    minDuration: Long,
    crossinline onClick: () -> Unit
): Modifier = composed {
    var lastInTime by remember { mutableStateOf(value = 0L) }
    clickable(
        indication = indication, interactionSource = interactionSource
    ) {
        var currentTimeMills = SystemClock.elapsedRealtime()
        if (currentTimeMills - lastInTime > minDuration) {
            lastInTime = currentTimeMills
            onClick.invoke()
        }
    }
}