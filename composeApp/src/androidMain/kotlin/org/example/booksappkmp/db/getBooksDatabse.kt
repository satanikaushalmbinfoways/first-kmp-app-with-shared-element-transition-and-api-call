package org.example.booksappkmp.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getBooksDatabase(ctx: Context): BooksDatabase {
    val dbFile = ctx.applicationContext.getDatabasePath("books.db")
    return Room.databaseBuilder(
        ctx.applicationContext,
        name = dbFile.absolutePath,
        klass = BooksDatabase::class.java
    ).setDriver(BundledSQLiteDriver()).build()
}
