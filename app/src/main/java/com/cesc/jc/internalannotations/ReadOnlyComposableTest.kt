package com.cesc.jc.internalannotations

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext


@Composable
@ReadOnlyComposable
fun getString(@StringRes id : Int) : String{
    return LocalContext.current.getString(id)
}