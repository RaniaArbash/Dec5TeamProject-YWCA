package com.example.dec5teamproject

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun FunContentScreen(
    viewModel: ContentViewModel = viewModel()
) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val booksState by viewModel.booksState.collectAsStateWithLifecycle()
    val jokesState by viewModel.jokesState.collectAsStateWithLifecycle()
    val gamesState by viewModel.gamesState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Fun Content & Recommendations",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C3E50)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "Discover books, enjoy jokes, and find free games",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tab Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabButton(
                text = "Books",
                icon = Icons.Default.AccountCircle,
                isSelected = selectedTab is ContentTab.Books,
                onClick = { viewModel.selectTab(ContentTab.Books) }
            )
            TabButton(
                text = "Jokes",
                icon = Icons.Default.Edit,
                isSelected = selectedTab is ContentTab.Jokes,
                onClick = { viewModel.selectTab(ContentTab.Jokes) }
            )
            TabButton(
                text = "Games",
                icon = Icons.Default.AddCircle,
                isSelected = selectedTab is ContentTab.Games,
                onClick = { viewModel.selectTab(ContentTab.Games) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on selected tab
        when (selectedTab) {
            ContentTab.Books -> ContentSection(booksState, viewModel::retryCurrentTab) { books ->
                BooksList(books)
            }
            ContentTab.Jokes -> ContentSection(jokesState, viewModel::retryCurrentTab) { jokes ->
                JokesList(jokes)
            }
            ContentTab.Games -> ContentSection(gamesState, viewModel::retryCurrentTab) { games ->
                GamesList(games)
            }
        }
    }
}

@Composable
fun RowScope. TabButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFF2196F3) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (isSelected) Color.White else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = if (isSelected) Color.White else Color.Gray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun <T> ContentSection(
    state: UiState<List<T>>,
    onRetry: () -> Unit = {},
    content: @Composable (List<T>) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        when (state) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Success -> {
                content(state.data)
            }
        }
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFFEBEE))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = Color(0xFFD32F2F),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD32F2F)
            )
        ) {
            Text("Try Again")
        }
    }
}

@Composable
fun BooksList(books: List<Book>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { book ->
            BookItem(book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            if (book.coverUrl.isNotEmpty()) {
                AsyncImage(
                    model = book.coverUrl,
                    contentDescription = book.title,
                    modifier = Modifier
                        .size(80.dp, 120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title ?: "Unknown Title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = book.authorString,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                book.first_publish_year?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Published: $it",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun JokesList(jokes: List<Joke>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(jokes) { joke ->
            JokeItem(joke)
        }
    }
}

@Composable
fun JokeItem(joke: Joke) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = joke.setup,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = joke.punchline,
                color = Color(0xFF1976D2),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = joke.type.uppercase(),
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun GamesList(games: List<Game>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(games) { game ->
            GameItem(game)
        }
    }
}

@Composable
fun GameItem(game: Game) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = game.thumbnail,
                contentDescription = game.title,
                modifier = Modifier
                    .size(100.dp, 70.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = game.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = game.short_description,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = game.genre,
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color(0xFF4CAF50), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = game.platform,
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color(0xFF2196F3), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}