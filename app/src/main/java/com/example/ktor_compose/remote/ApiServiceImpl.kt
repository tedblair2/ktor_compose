package com.example.ktor_compose.remote

import com.example.ktor_compose.model.Quote
import com.example.ktor_compose.model.ApiResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {
    override fun getQuotes(category: String, limit: Int): Flow<ApiResult<List<Quote>>> = flow{
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get("/v1/quotes"){
                parameter("category",category)
                parameter("limit",limit)
            }.body()))
        }catch (e:Exception){
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }
}
