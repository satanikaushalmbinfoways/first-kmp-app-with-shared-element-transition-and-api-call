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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    navController: NavController,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val imageUrl by remember {
        mutableStateOf(
            navController.previousBackStackEntry?.savedStateHandle?.get(
                "image_url"
            ) ?: ""
        )
    }
    val title by remember {
        mutableStateOf(
            navController.previousBackStackEntry?.savedStateHandle?.get(
                "title"
            ) ?: ""
        )
    }
    val description by remember {
        mutableStateOf(
            navController.previousBackStackEntry?.savedStateHandle?.get(
                "description"
            ) ?: ""
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
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
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
                    model = imageUrl,
                    contentDescription = null,
                    loading = {
                        CircularProgressIndicator()
                    },
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = imageUrl),
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
                    text = title,
                    fontFamily = Serif,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            state = rememberSharedContentState(key = title),
                            animatedVisibilityScope = animatedContentScope
                        ).skipToLookaheadSize(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = description,
                    fontFamily = Serif,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(key = description),
                        animatedVisibilityScope = animatedContentScope
                    ).skipToLookaheadSize()
                )
            }
        }
    }

}
