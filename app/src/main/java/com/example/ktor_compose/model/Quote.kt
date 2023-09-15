package com.example.ktor_compose.model

@kotlinx.serialization.Serializable
data class Quote(
    val author: String?,
    val category: String?,
    val quote: String?
)
