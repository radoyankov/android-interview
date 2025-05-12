package com.reddit.rickandmortyapp.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.reddit.rickandmortyapp.network.AppDatabase
import com.reddit.rickandmortyapp.network.RickAndMortyRepository
import com.reddit.rickandmortyapp.network.RickAndMortyService
import com.reddit.rickandmortyapp.network.RickAndMortyViewModel

@Composable
fun Feed() {
    val c = LocalContext.current.applicationContext
    val db = Room.databaseBuilder(c, AppDatabase::class.java, "rickandmorty-db").build()
    val service = remember { RickAndMortyService() }
    val repository = remember { RickAndMortyRepository(service.api, db.charactersCacheDao()) }

    val viewModel = remember { RickAndMortyViewModel(repository) }
    val fetchedItems by viewModel.items
    val error by viewModel.error
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(
                message = it
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        val lazyListState = rememberLazyListState()

        LaunchedEffect(lazyListState.canScrollForward) {
            if (lazyListState.canScrollForward.not() && lazyListState.firstVisibleItemIndex > 1) {
                if (!viewModel.isLoading.value && viewModel.hasMoreData.value) {
                    viewModel.loadNextPage()
                }
            }
        }

        LazyColumn(
            contentPadding = padding,
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            reverseLayout = false
        ) {
            items(fetchedItems) {
                RickAndMortyCharacter(it)
            }
            item {
                if (viewModel.isLoading.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}