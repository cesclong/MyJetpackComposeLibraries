package com.cesc.backlog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp


@Composable
fun SubComposeLayoutDemo() {
    BoxWithConstraints(modifier = Modifier.height(100.dp)) {
        Log.e("Test", "maxHeight:${maxHeight}")
        val rectHeight = 100.dp
        if (maxHeight < rectHeight * 2) {
            Box(modifier = Modifier
                .size(50.dp, rectHeight)
                .background(Color.Blue))
        }else{
            Column {
                Box(modifier = Modifier
                    .size(50.dp, rectHeight)
                    .background(Color.Blue))

                Box(modifier = Modifier
                    .size(50.dp, rectHeight)
                    .background(Color.Gray))
            }
        }

    }
}