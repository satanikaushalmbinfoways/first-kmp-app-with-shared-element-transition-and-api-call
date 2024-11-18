package org.example.booksappkmp.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily.Companion.Serif
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import kotlinx.serialization.json.Json
import org.example.booksappkmp.modal.BooksListResponseItem

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookDetailsScreen(
    navController: NavController,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val data by remember {
        mutableStateOf(
            Json.decodeFromString<BooksListResponseItem>(navController.previousBackStackEntry?.savedStateHandle?.get("data") ?: "")
        )
    }
    with(sharedTransitionScope) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Book Details"
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .fillMaxSize().padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = data.cover,
                    contentDescription = null,
                    loading = {
                        CircularProgressIndicator()
                    },
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = data.cover),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .height(300.dp)
                        .clip(
                            RoundedCornerShape(10)
                        ),
                    contentScale = ContentScale.Fit,
                    clipToBounds = true,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = data.title,
                    fontFamily = Serif,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                        state = rememberSharedContentState(key = data.title),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = data.description,
                    fontFamily = Serif,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.sharedBounds(
                        sharedContentState = rememberSharedContentState(key = data.description),
                        animatedVisibilityScope = animatedContentScope
                    )
                )
            }
        }
    }

}
