package com.cesc.navigations

import androidx.navigation.NavController


sealed class NavRoutes(val route: String) {
    object Main : NavRoutes("main")

    object Ptr : NavRoutes("ptr")
}

fun NavRoutes.navigate(navController: NavController) {
    navController.navigate(this.route)
}
