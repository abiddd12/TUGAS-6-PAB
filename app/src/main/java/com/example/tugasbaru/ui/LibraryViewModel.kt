package com.example.tugasbaru.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasbaru.data.Book
import com.example.tugasbaru.data.LibraryDatabase
import com.example.tugasbaru.data.Loan
import com.example.tugasbaru.data.LoanWithBook
import com.example.tugasbaru.data.SampleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

const val LOAN_DAYS = 14
const val MAX_ACTIVE_LOANS = 3

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = LibraryDatabase.getInstance(application).libraryDao()

    val books: StateFlow<List<Book>> = dao.getBooks().stateInViewModel(emptyList())
    val activeLoans: StateFlow<List<LoanWithBook>> = dao.getActiveLoans().stateInViewModel(emptyList())
    val loanHistory: StateFlow<List<LoanWithBook>> = dao.getLoanHistory().stateInViewModel(emptyList())

    init {
        viewModelScope.launch {
            if (dao.countBooks() == 0) dao.insertBooks(SampleData.books)
        }
    }

    fun book(id: Int): Flow<Book?> = dao.getBook(id)

    fun activeLoan(bookId: Int): Flow<Loan?> = dao.getActiveLoan(bookId)

    fun borrow(bookId: Int) {
        if (activeLoans.value.size >= MAX_ACTIVE_LOANS) return
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            dao.borrowBook(bookId, now, now + TimeUnit.DAYS.toMillis(LOAN_DAYS.toLong()))
        }
    }

    fun returnBook(loan: Loan) {
        viewModelScope.launch { dao.returnBook(loan, System.currentTimeMillis()) }
    }

    private fun <T> Flow<T>.stateInViewModel(initial: T): StateFlow<T> =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initial)
}
