package org.example.booksappkmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.booksappkmp.data.network.client
import org.example.booksappkmp.db.BooksDao
import org.example.booksappkmp.modal.BooksListResponseItem
import kotlin.reflect.KClass

class BooksViewModelProvider(private val booksDao: BooksDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return BooksViewModel(booksDao) as T
    }
}

class BooksViewModel(private val booksDao : BooksDao) : ViewModel() {
    private val _booksResponse = MutableStateFlow(emptyList<BooksListResponseItem>())
    val booksResponse = _booksResponse.asStateFlow()
    var isLoading = MutableStateFlow(false)
        private set
    private var apiJob : Job? = null
    private fun callBooksListApi() {
        apiJob?.cancel()
        isLoading.value = true
        apiJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {

                    client.get {
                        val response = client.get {
                            url("en/books")
                        }.body() as List<BooksListResponseItem>
                        _booksResponse.update {
                            response
                        }
                        booksDao.insertBooks(response)
                    }
                }catch (e : Exception) {
                    println(e.message)
                }finally {
                    isLoading.value = false
                }
            }
        }
    }

    init {
        callBooksListApi()
    }

}
