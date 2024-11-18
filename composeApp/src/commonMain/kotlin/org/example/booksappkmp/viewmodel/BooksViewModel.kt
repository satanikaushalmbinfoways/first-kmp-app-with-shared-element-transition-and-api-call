package org.example.booksappkmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.booksappkmp.data.network.client
import org.example.booksappkmp.modal.BooksListResponseItem

class BooksViewModel : ViewModel() {
    private val _booksResponse = MutableStateFlow(emptyList<BooksListResponseItem>())
    val booksResponse = _booksResponse.asStateFlow()
    var isLoading = MutableStateFlow(false)
        private set
    private var apiJob : Job? = null
    fun callBooksListApi() {
        apiJob?.cancel()
        isLoading.value = true
        apiJob = viewModelScope.launch {
            client.get {
                val response = client.get {
                    url("en/books")
                }.body() as List<BooksListResponseItem>
                _booksResponse.update {
                    response
                }
                isLoading.value = false
            }
        }
    }

    init {
        callBooksListApi()
    }

}
