package com.cesc.navigations

import androidx.navigation.NavOptions

interface NavInterceptor {

    fun shouldIntercept(
        route : String,
        navOptions : NavOptions?
    )

}