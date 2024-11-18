package org.example.booksappkmp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.EaseInOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.booksappkmp.presentation.BookDetailsScreen
import org.example.booksappkmp.presentation.BooksListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App() {

    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController,
            startDestination = "list_screen",
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
            enterTransition = {
                scaleIn(
                    initialScale = 0.9f,
                    animationSpec = tween(
                        easing = EaseInOutExpo
                    )
                ) + fadeIn(animationSpec = tween(
                    easing = EaseInOutExpo
                )
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 1.1f,
                    animationSpec = tween(
                        easing = EaseInOutExpo
                    )
                ) + fadeOut(animationSpec = tween(
                    easing = EaseInOutExpo
                )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 1.1f,
                    animationSpec = tween(
                        easing = EaseInOutExpo
                    )
                ) + fadeIn(animationSpec = tween(
                    easing = EaseInOutExpo
                )
                )
            },
            popExitTransition = {
                scaleOut(
                    targetScale = 0.9f,
                    animationSpec = tween(
                        easing = EaseInOutExpo
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        easing = EaseInOutExpo
                    )
                )
            }
        ) {
            composable(
                "list_screen"
            ) {
                BooksListScreen(navController, this, this@SharedTransitionLayout)
            }

            composable("details_screen") {
                BookDetailsScreen(navController, this, this@SharedTransitionLayout)
            }
        }
    }




}
