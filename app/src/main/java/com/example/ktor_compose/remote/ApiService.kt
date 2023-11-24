package com.example.ktor_compose.remote

import com.example.ktor_compose.model.ApiResult
import com.example.ktor_compose.model.Quote
import kotlinx.coroutines.flow.Flow

interface ApiService {
    fun getQuotes(category:String="humor",limit:Int=5):Flow<ApiResult<List<Quote>>>
}
