package com.cesc.jc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cesc.MainRootScreen
import com.cesc.jc.ui.theme.MyJetpackComposeLibrariesTheme
import com.cesc.navigations.NavRoutes
import com.cesc.pulltorefresh.PullToRefreshDemoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyJetpackComposeLibrariesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    TestImageLoader()
//                    SubComposeLayoutDemo()
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
private fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.Main.route) {
        composable(NavRoutes.Main.route) {
            MainRootScreen(navController = navController)
        }

        composable(NavRoutes.Ptr.route) {
            PullToRefreshDemoScreen(navController = navController)
        }
    }
}

@Composable
private fun TestBrushLayout() {
    val colorStops = arrayOf(
        0.0f to Color.Yellow,
        0.5f to Color.Red,
        1f to Color.Blue
    )
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            BrushView(
                modifier = Modifier
                    .weight(1f)
                    .height(110.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))
            BrushView(
                modifier = Modifier
                    .weight(1f)
                    .height(110.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            BrushView(
                modifier = Modifier
                    .weight(1f)
                    .height(110.dp)
            )

        }

    }
}

@Composable
private fun BrushView(modifier: Modifier = Modifier) {
    val colorStops = arrayOf(
        0.0f to Color.Yellow,
        0.8f to Color.Yellow,
        1f to Color.Blue
    )

    Spacer(
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colorStops = colorStops,
                startY = 0f,
                endY = Float.POSITIVE_INFINITY
            )
        )
    )
}