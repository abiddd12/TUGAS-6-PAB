package com.example.tugasbaru.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tugasbaru.R
import com.example.tugasbaru.data.Book
import com.example.tugasbaru.data.Loan
import com.example.tugasbaru.data.SampleData
import com.example.tugasbaru.ui.LOAN_DAYS
import com.example.tugasbaru.ui.MAX_ACTIVE_LOANS
import com.example.tugasbaru.ui.components.AvailabilityChip
import com.example.tugasbaru.ui.components.BookCover
import com.example.tugasbaru.ui.dueDateLabel
import com.example.tugasbaru.ui.theme.TUGASBARUTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    book: Book?,
    activeLoan: Loan?,
    loanLimitReached: Boolean,
    onBack: () -> Unit,
    onBorrowClick: () -> Unit,
    onReturnClick: (Loan) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Buku") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (book == null) return@Scaffold

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BookCover(book, Modifier.size(width = 160.dp, height = 230.dp))
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SuggestionChip(onClick = {}, label = { Text(book.category) })
                SuggestionChip(onClick = {}, label = { Text(book.year.toString()) })
                SuggestionChip(onClick = {}, label = { Text("${book.pages} hlm") })
            }
            AvailabilityChip(book.available)

            Text(
                text = "Sinopsis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = book.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            when {
                activeLoan != null -> {
                    InfoCard("Kamu sedang meminjam buku ini. ${dueDateLabel(activeLoan.dueDate)}.")
                    OutlinedButton(
                        onClick = { onReturnClick(activeLoan) },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Kembalikan Buku") }
                }
                !book.available -> {
                    InfoCard("Buku ini sedang dipinjam anggota lain.")
                }
                loanLimitReached -> {
                    InfoCard("Kamu sudah meminjam $MAX_ACTIVE_LOANS buku. Kembalikan salah satu dulu untuk meminjam lagi.")
                }
                else -> {
                    Button(onClick = onBorrowClick, modifier = Modifier.fillMaxWidth()) {
                        Text("Pinjam Buku ($LOAN_DAYS hari)")
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Text(text, modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailScreenPreview() {
    TUGASBARUTheme {
        BookDetailScreen(
            book = SampleData.books[0],
            activeLoan = null,
            loanLimitReached = false,
            onBack = {},
            onBorrowClick = {},
            onReturnClick = {}
        )
    }
}
