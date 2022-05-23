package com.movieapp.presentation.home

import com.movieapp.domain.model.Home

data class HomeUiState(
    val isLoading: Boolean = false,
    val home: List<List<Home>> = emptyList(),
    val error: String = ""
)
