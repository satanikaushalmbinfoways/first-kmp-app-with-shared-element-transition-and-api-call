package org.example.booksappkmp.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily.Companion.Serif
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.booksappkmp.viewmodel.BooksViewModel

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BooksListScreen(
    navController: NavController,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val booksViewModel = viewModel<BooksViewModel>()
    val booksList by booksViewModel.booksResponse.collectAsStateWithLifecycle()
    val isLoading by booksViewModel.isLoading.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    with(sharedTransitionScope) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("My Books List", fontFamily = Serif)
                            }
                        )
                    }
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        state = listState,
                        contentPadding = PaddingValues(10.dp, 25.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(
                            booksList,
                            key = {
                                it.number
                            },
                            contentType = {
                                it
                            }
                        ) {
                            ListItem(
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember {
                                            MutableInteractionSource()
                                        },
                                        null,
                                        onClick = {
                                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                                "data",
                                                Json.encodeToString(it)
                                            )
                                            navController.navigate("details_screen") {
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 10.dp
                                    )
                                    .clip(shape = RoundedCornerShape(10.dp)),
                                headlineContent = {
                                    Text(
                                        text = it.title,
                                        fontFamily = Serif,
                                        modifier = Modifier.sharedElement(
                                            state = rememberSharedContentState(key = it.title),
                                            animatedVisibilityScope = animatedContentScope
                                        ),
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                },
                                leadingContent = {
                                    AsyncImage(
                                        model = it.cover,
                                        contentDescription = null,
//                                        loading = {
//                                            CircularProgressIndicator()
//                                        },
                                        modifier = Modifier
//                                        .padding(vertical = 8.dp)
                                            .sharedElement(
                                                state = rememberSharedContentState(key = it.cover),
                                                animatedVisibilityScope = animatedContentScope
                                            )
                                            .height(160.dp)
                                            .clip(
                                                RoundedCornerShape(10)
                                            ),
                                        contentScale = ContentScale.Fit,
                                        clipToBounds = true,
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        text = it.description,
                                        fontFamily = Serif,
                                        fontSize = 13.sp,
                                        maxLines = 4,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .sharedElement(
                                                state = rememberSharedContentState(key = it.description),
                                                animatedVisibilityScope = animatedContentScope
                                            ).padding(
                                                vertical = 12.dp
                                            )
                                    )
                                },
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.LightGray.copy(0.4f),
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}
