package com.example.MoviesAPi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.MoviesAPi.api.createTmdbApi
import com.example.MoviesAPi.data.model.MovieData
import com.example.MoviesAPi.data.repository.MovieRepository
import com.example.MoviesAPi.ui.theme.BlueVariant
import com.example.MoviesAPi.ui.theme.HitemaGray
import com.example.MoviesAPi.ui.theme.HitemaMenu
import com.example.MoviesAPi.ui.theme.White
import com.example.MoviesAPi.ui.theme.Yellow

@Composable
fun MainScreen() {

    val movieRepository = MovieRepository(createTmdbApi())

    Scaffold(
        topBar = {
            DefaultAppBar(
                onSearchClicked = {}
            )
        },
        bottomBar = { BottomMenu() }
    ) {padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = padding.calculateBottomPadding() + 24.dp,
                )
        ) {
            item {
                Text(
                    text = "Popular Movies",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                PopularMovies(movieRepository = movieRepository)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Top Rated",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                TopRatedMovies(movieRepository = movieRepository)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Upcoming",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                UpcomingMovies(movieRepository = movieRepository)
            }
        }
    }
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Movies List"
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {})
}

@Composable
fun BottomMenu() {
    BottomAppBar(backgroundColor = HitemaMenu) {
        BottomNavigationItem(
            selected = true,
            onClick = { /* Action lorsque Home est cliqué */ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
            },
            label = { Text("Home") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { /* Action lorsque Library est cliqué */ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Library",
                    tint = Color.White
                )
            },
            label = { Text("Liked") }
        )
    }
}

@Composable
@Preview
fun BottomMenuPreview() {
    BottomMenu()
}

@Composable
fun PopularMovies(movieRepository: MovieRepository) {
    val moviesState = remember { mutableStateListOf<MovieData>() }
    val effectKey = rememberUpdatedState(Any())

    LaunchedEffect(effectKey.value) {
        val nowPlayingMovies = movieRepository.getPopularMovies()
        moviesState.clear()
        moviesState.addAll(nowPlayingMovies)
    }

    LazyRow (contentPadding = PaddingValues(start = 24.dp)) {
        itemsIndexed(moviesState) { index, movie ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .padding(end = 24.dp),
                contentAlignment = Alignment.BottomCenter

            ) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.BottomCenter

                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/original" + movie.posterPath,
                            contentDescription = movie.title,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(width = 200.dp, height = 260.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(
                                    HitemaGray
                                )
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                movie.title, style = MaterialTheme.typography.subtitle1.copy(
                                    color = White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPopularMovies() {
    val movieRepository = MovieRepository(createTmdbApi())
    PopularMovies(movieRepository = movieRepository)
}

@Composable
fun TopRatedMovies(movieRepository: MovieRepository) {
    val moviesState = remember { mutableStateListOf<MovieData>() }
    val effectKey = rememberUpdatedState(Any())

    LaunchedEffect(effectKey.value) {
        val nowPlayingMovies = movieRepository.getTopRatedMovies()
        moviesState.clear()
        moviesState.addAll(nowPlayingMovies)
    }

    LazyRow (contentPadding = PaddingValues(start = 24.dp)) {
        itemsIndexed(moviesState) { index, movie ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .padding(end = 24.dp),
                contentAlignment = Alignment.BottomCenter

            ) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.BottomCenter

                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/original" + movie.posterPath,
                            contentDescription = movie.title,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(width = 200.dp, height = 260.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(
                                    HitemaGray
                                )
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                movie.title, style = MaterialTheme.typography.subtitle1.copy(
                                    color = White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopRatedMovies() {
    val movieRepository = MovieRepository(createTmdbApi())
    TopRatedMovies(movieRepository = movieRepository)
}

@Composable
fun UpcomingMovies(movieRepository: MovieRepository) {
    val moviesState = remember { mutableStateListOf<MovieData>() }
    val effectKey = rememberUpdatedState(Any())

    LaunchedEffect(effectKey.value) {
        val nowPlayingMovies = movieRepository.getUpcomingMovies()
        moviesState.clear()
        moviesState.addAll(nowPlayingMovies)
    }

    LazyRow (contentPadding = PaddingValues(start = 24.dp)) {
        itemsIndexed(moviesState) { index, movie ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .padding(end = 24.dp),
                contentAlignment = Alignment.BottomCenter

            ) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.BottomCenter

                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/original" + movie.posterPath,
                            contentDescription = movie.title,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(width = 200.dp, height = 260.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(
                                    HitemaGray
                                )
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                movie.title, style = MaterialTheme.typography.subtitle1.copy(
                                    color = White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUpcomingMovies() {
    val movieRepository = MovieRepository(createTmdbApi())
    UpcomingMovies(movieRepository = movieRepository)
}