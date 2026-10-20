package com.example.tugasbaru.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tugasbaru.data.Book
import com.example.tugasbaru.data.SampleData
import com.example.tugasbaru.ui.components.BookGridItem
import com.example.tugasbaru.ui.components.BookListItem
import com.example.tugasbaru.ui.theme.TUGASBARUTheme

@Composable
fun HomeScreen(
    books: List<Book>,
    activeLoanCount: Int,
    onBookClick: (Book) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Selamat datang 👋",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Mau baca apa hari ini?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Dipinjam", activeLoanCount.toString(), Modifier.weight(1f))
                StatCard("Koleksi", books.size.toString(), Modifier.weight(1f))
                StatCard("Tersedia", books.count { it.available }.toString(), Modifier.weight(1f))
            }
        }

        item { SectionHeader("Buku Populer", onSeeAllClick) }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(books.take(6), key = { it.id }) { book ->
                    BookGridItem(book, onClick = { onBookClick(book) })
                }
            }
        }

        item { SectionHeader("Baru Ditambahkan", onSeeAllClick) }
        items(books.sortedByDescending { it.year }.take(4), key = { it.id }) { book ->
            BookListItem(book, onClick = { onBookClick(book) })
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun SectionHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        TextButton(onClick = onSeeAllClick) { Text("Lihat semua") }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TUGASBARUTheme {
        HomeScreen(SampleData.books, activeLoanCount = 2, onBookClick = {}, onSeeAllClick = {})
    }
}
