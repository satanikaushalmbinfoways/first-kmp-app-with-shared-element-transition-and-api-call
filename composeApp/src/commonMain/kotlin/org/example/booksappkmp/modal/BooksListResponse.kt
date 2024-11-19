package org.example.booksappkmp.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "books_table")
data class BooksListResponseItem(
    val cover: String = "",
    val description: String = "",
    val index: Int = 0,
    @PrimaryKey
    val number: Int = 0,
    val originalTitle: String = "",
    val pages: Int = 0,
    val releaseDate: String = "",
    val title: String = ""
)
