package org.example.booksappkmp.modal

import kotlinx.serialization.Serializable

@Serializable
data class BooksListResponseItem(
    val cover: String = "",
    val description: String = "",
    val index: Int = 0,
    val number: Int = 0,
    val originalTitle: String = "",
    val pages: Int = 0,
    val releaseDate: String = "",
    val title: String = ""
)
