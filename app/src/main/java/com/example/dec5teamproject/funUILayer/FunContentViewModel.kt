package com.example.dec5teamproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ContentTab {
    object Books : ContentTab()
    object Jokes : ContentTab()
    object Games : ContentTab()
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ContentViewModel : ViewModel() {

    private val _selectedTab = MutableStateFlow<ContentTab>(ContentTab.Books)
    val selectedTab: StateFlow<ContentTab> = _selectedTab

    private val _booksState = MutableStateFlow<UiState<List<Book>>>(UiState.Loading)
    val booksState: StateFlow<UiState<List<Book>>> = _booksState

    private val _jokesState = MutableStateFlow<UiState<List<Joke>>>(UiState.Loading)
    val jokesState: StateFlow<UiState<List<Joke>>> = _jokesState

    private val _gamesState = MutableStateFlow<UiState<List<Game>>>(UiState.Loading)
    val gamesState: StateFlow<UiState<List<Game>>> = _gamesState

    init {
        loadBooks()
    }

    fun selectTab(tab: ContentTab) {
        _selectedTab.value = tab
        when (tab) {
            ContentTab.Books -> if (_booksState.value is UiState.Loading) loadBooks()
            ContentTab.Jokes -> if (_jokesState.value is UiState.Loading) loadJokes()
            ContentTab.Games -> if (_gamesState.value is UiState.Loading) loadGames()
        }
    }

    fun loadBooks() {
        viewModelScope.launch {
            _booksState.value = UiState.Loading
            try {
                val response = ApiClient.openLibraryApi.searchBooks()
                _booksState.value = UiState.Success(response.docs)
            } catch (e: Exception) {
                _booksState.value = UiState.Error("Failed to load books")
            }
        }
    }

    fun loadJokes() {
        viewModelScope.launch {
            _jokesState.value = UiState.Loading
            try {
                val jokes = ApiClient.jokeApi.getRandomJokes()
                _jokesState.value = UiState.Success(jokes)
            } catch (e: Exception) {
                _jokesState.value = UiState.Error("Failed to load jokes")
            }
        }
    }

    fun loadGames() {
        viewModelScope.launch {
            _gamesState.value = UiState.Loading
            try {
                val games = ApiClient.gameApi.getGames()
                _gamesState.value = UiState.Success(games)
            } catch (e: Exception) {
                _gamesState.value = UiState.Error("Failed to load games")
            }
        }
    }

    fun retryCurrentTab() {
        when (_selectedTab.value) {
            ContentTab.Books -> loadBooks()
            ContentTab.Jokes -> loadJokes()
            ContentTab.Games -> loadGames()
        }
    }
}