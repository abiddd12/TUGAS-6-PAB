package com.example.tugasbaru.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LibraryDao {

    @Query("SELECT * FROM books ORDER BY title")
    abstract fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :id")
    abstract fun getBook(id: Int): Flow<Book?>

    @Query("SELECT COUNT(*) FROM books")
    abstract suspend fun countBooks(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertBooks(books: List<Book>)

    @Transaction
    @Query("SELECT * FROM loans WHERE returnDate IS NULL ORDER BY dueDate")
    abstract fun getActiveLoans(): Flow<List<LoanWithBook>>

    @Transaction
    @Query("SELECT * FROM loans WHERE returnDate IS NOT NULL ORDER BY returnDate DESC")
    abstract fun getLoanHistory(): Flow<List<LoanWithBook>>

    @Query("SELECT * FROM loans WHERE bookId = :bookId AND returnDate IS NULL LIMIT 1")
    abstract fun getActiveLoan(bookId: Int): Flow<Loan?>

    @Query("SELECT available FROM books WHERE id = :bookId")
    protected abstract suspend fun isAvailable(bookId: Int): Boolean

    @Insert
    protected abstract suspend fun insertLoan(loan: Loan)

    @Query("UPDATE books SET available = :available WHERE id = :bookId")
    protected abstract suspend fun setAvailable(bookId: Int, available: Boolean)

    @Query("UPDATE loans SET returnDate = :returnDate WHERE id = :loanId")
    protected abstract suspend fun setReturnDate(loanId: Long, returnDate: Long)

    /** Mencatat peminjaman dan menandai buku dipinjam. Mengembalikan false jika buku tidak tersedia. */
    @Transaction
    open suspend fun borrowBook(bookId: Int, borrowDate: Long, dueDate: Long): Boolean {
        if (!isAvailable(bookId)) return false
        insertLoan(Loan(bookId = bookId, borrowDate = borrowDate, dueDate = dueDate))
        setAvailable(bookId, false)
        return true
    }

    @Transaction
    open suspend fun returnBook(loan: Loan, returnDate: Long) {
        setReturnDate(loan.id, returnDate)
        setAvailable(loan.bookId, true)
    }
}
