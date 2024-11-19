package org.example.booksappkmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.example.booksappkmp.db.getBooksDatabase

fun MainViewController() = ComposeUIViewController {
    val dao = remember {
        getBooksDatabase().booksDao()
    }
    App(dao)
}
