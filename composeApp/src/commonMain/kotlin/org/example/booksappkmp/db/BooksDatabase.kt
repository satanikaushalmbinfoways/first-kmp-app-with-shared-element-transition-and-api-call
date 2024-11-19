package org.example.booksappkmp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.booksappkmp.modal.BooksListResponseItem

@Database(entities = [BooksListResponseItem::class], version = 1)
abstract class BooksDatabase : RoomDatabase(){
    abstract fun booksDao() : BooksDao
}
