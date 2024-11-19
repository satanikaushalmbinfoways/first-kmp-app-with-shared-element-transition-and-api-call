package org.example.booksappkmp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.booksappkmp.modal.BooksListResponseItem

@Dao
interface BooksDao {

    @Query("SELECT * FROM books_table")
    fun getAllBooks() : Flow<List<BooksListResponseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = BooksListResponseItem::class)
    fun insertBooks(list: List<BooksListResponseItem>)
}
