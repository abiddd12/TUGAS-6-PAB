package com.example.tugasbaru.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "books")
data class Book(
    @PrimaryKey val id: Int,
    val title: String,
    val author: String,
    val category: String,
    val year: Int,
    val pages: Int,
    val description: String,
    /** Warna sampul dalam format ARGB, contoh 0xFF5C6BC0. */
    val coverColor: Long,
    val available: Boolean = true,
)

@Entity(
    tableName = "loans",
    foreignKeys = [ForeignKey(entity = Book::class, parentColumns = ["id"], childColumns = ["bookId"])],
    indices = [Index("bookId")]
)
data class Loan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: Int,
    val borrowDate: Long,
    val dueDate: Long,
    /** null berarti buku belum dikembalikan. */
    val returnDate: Long? = null,
)

data class LoanWithBook(
    @Embedded val loan: Loan,
    @Relation(parentColumn = "bookId", entityColumn = "id") val book: Book,
)
