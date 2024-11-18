package org.example.booksappkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform