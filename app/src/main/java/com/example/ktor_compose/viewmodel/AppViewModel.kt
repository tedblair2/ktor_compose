package com.example.ktor_compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktor_compose.model.ApiResult
import com.example.ktor_compose.model.Quote
import com.example.ktor_compose.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val apiService: ApiService,
                                       private val defaultDispatcher: CoroutineDispatcher):ViewModel() {
    private val _quotes= MutableStateFlow<ApiResult<List<Quote>>>(ApiResult.Loading())
    val quotes= _quotes.asStateFlow()

    init {
        fetchQuotes()
    }

    private fun fetchQuotes(){
        viewModelScope.launch {
            apiService.getQuotes()
                .flowOn(defaultDispatcher)
                .catch {
                    _quotes.value=ApiResult.Error(it.message ?: "Something went wrong")
                }
                .collect{
                    _quotes.value=it
                }
        }
    }
}