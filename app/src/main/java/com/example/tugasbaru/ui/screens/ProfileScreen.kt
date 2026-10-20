package com.example.tugasbaru.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tugasbaru.data.Book
import com.example.tugasbaru.data.Loan
import com.example.tugasbaru.data.LoanWithBook
import com.example.tugasbaru.data.SampleData
import com.example.tugasbaru.ui.MAX_ACTIVE_LOANS
import com.example.tugasbaru.ui.components.BookListItem
import com.example.tugasbaru.ui.dueDateLabel
import com.example.tugasbaru.ui.formatDate
import com.example.tugasbaru.ui.theme.TUGASBARUTheme

@Composable
fun ProfileScreen(
    activeLoans: List<LoanWithBook>,
    loanHistory: List<LoanWithBook>,
    onBookClick: (Book) -> Unit,
    onReturnClick: (Loan) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { ProfileHeader() }

        item { SectionTitle("Sedang Dipinjam (${activeLoans.size}/$MAX_ACTIVE_LOANS)") }
        if (activeLoans.isEmpty()) {
            item { EmptyText("Belum ada buku yang dipinjam. Cari buku di Katalog.") }
        }
        items(activeLoans, key = { "aktif-${it.loan.id}" }) { (loan, book) ->
            Column {
                BookListItem(
                    book = book,
                    onClick = { onBookClick(book) },
                    trailingInfo = dueDateLabel(loan.dueDate)
                )
                TextButton(
                    onClick = { onReturnClick(loan) },
                    modifier = Modifier.align(Alignment.End)
                ) { Text("Kembalikan") }
            }
        }

        item { SectionTitle("Riwayat Peminjaman") }
        if (loanHistory.isEmpty()) {
            item { EmptyText("Belum ada riwayat.") }
        }
        items(loanHistory, key = { "riwayat-${it.loan.id}" }) { (loan, book) ->
            BookListItem(
                book = book,
                onClick = { onBookClick(book) },
                trailingInfo = "Dikembalikan ${formatDate(loan.returnDate ?: loan.dueDate)}"
            )
        }

        item {
            Card(modifier = Modifier.padding(top = 8.dp)) {
                Column {
                    listOf("Pengaturan Akun", "Bantuan").forEachIndexed { index, menu ->
                        if (index > 0) HorizontalDivider()
                        ListItem(headlineContent = { Text(menu) })
                    }
                }
            }
        }

        item {
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Keluar")
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
private fun EmptyText(text: String) {
    Text(text, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
private fun ProfileHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MS",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(Modifier.height(12.dp))
            Text("Muhammad Syuja", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Anggota • ID 2026-0142", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val now = System.currentTimeMillis()
    val day = 86_400_000L
    TUGASBARUTheme {
        ProfileScreen(
            activeLoans = listOf(
                LoanWithBook(Loan(1, 2, now, now + 4 * day), SampleData.books[1]),
            ),
            loanHistory = listOf(
                LoanWithBook(Loan(2, 3, now - 20 * day, now - 6 * day, now - 8 * day), SampleData.books[2]),
            ),
            onBookClick = {},
            onReturnClick = {}
        )
    }
}
