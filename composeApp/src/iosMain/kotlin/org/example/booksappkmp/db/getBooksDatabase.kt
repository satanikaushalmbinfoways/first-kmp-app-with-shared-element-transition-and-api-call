package org.example.booksappkmp.db

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun getBooksDatabase() : BooksDatabase {
    val dbFile = NSHomeDirectory() + "books.db"
    return Room.databaseBuilder<BooksDatabase>(
        name = dbFile,
        factory = { BooksDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}
