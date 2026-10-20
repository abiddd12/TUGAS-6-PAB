package com.example.tugasbaru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasbaru.data.Book
import com.example.tugasbaru.data.Loan
import com.example.tugasbaru.data.LoanWithBook
import com.example.tugasbaru.data.SampleData
import com.example.tugasbaru.ui.LibraryViewModel
import com.example.tugasbaru.ui.MAX_ACTIVE_LOANS
import com.example.tugasbaru.ui.screens.BookDetailScreen
import com.example.tugasbaru.ui.screens.CatalogScreen
import com.example.tugasbaru.ui.screens.HomeScreen
import com.example.tugasbaru.ui.screens.ProfileScreen
import com.example.tugasbaru.ui.theme.TUGASBARUTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TUGASBARUTheme {
                TUGASBARUApp()
            }
        }
    }
}

@Composable
fun TUGASBARUApp(viewModel: LibraryViewModel = viewModel()) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.BERANDA) }
    var selectedBookId by rememberSaveable { mutableStateOf<Int?>(null) }
    // Menyimpan state tiap tab (pencarian, filter, scroll) saat membuka detail buku atau pindah tab.
    val tabStateHolder = rememberSaveableStateHolder()

    val books by viewModel.books.collectAsState()
    val activeLoans by viewModel.activeLoans.collectAsState()
    val loanHistory by viewModel.loanHistory.collectAsState()
    val openDetail: (Book) -> Unit = { selectedBookId = it.id }

    selectedBookId?.let { bookId ->
        val book by remember(bookId) { viewModel.book(bookId) }.collectAsState(null)
        val activeLoan by remember(bookId) { viewModel.activeLoan(bookId) }.collectAsState(null)

        BackHandler { selectedBookId = null }
        BookDetailScreen(
            book = book,
            activeLoan = activeLoan,
            loanLimitReached = activeLoans.size >= MAX_ACTIVE_LOANS,
            onBack = { selectedBookId = null },
            onBorrowClick = { viewModel.borrow(bookId) },
            onReturnClick = viewModel::returnBook
        )
        return
    }

    tabStateHolder.SaveableStateProvider(currentDestination.name) {
        LibraryHome(
            currentDestination = currentDestination,
            onDestinationChange = { currentDestination = it },
            books = books,
            activeLoans = activeLoans,
            loanHistory = loanHistory,
            onBookClick = openDetail,
            onReturnClick = viewModel::returnBook
        )
    }
}

@Composable
fun LibraryHome(
    currentDestination: AppDestinations,
    onDestinationChange: (AppDestinations) -> Unit,
    books: List<Book>,
    activeLoans: List<LoanWithBook>,
    loanHistory: List<LoanWithBook>,
    onBookClick: (Book) -> Unit,
    onReturnClick: (Loan) -> Unit,
) {
    // Back dari tab Katalog/Profil kembali ke Beranda; Back di Beranda keluar aplikasi.
    BackHandler(enabled = currentDestination != AppDestinations.BERANDA) {
        onDestinationChange(AppDestinations.BERANDA)
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { onDestinationChange(it) }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when (currentDestination) {
                AppDestinations.BERANDA -> HomeScreen(
                    books = books,
                    activeLoanCount = activeLoans.size,
                    onBookClick = onBookClick,
                    onSeeAllClick = { onDestinationChange(AppDestinations.KATALOG) },
                    modifier = modifier
                )
                AppDestinations.KATALOG -> CatalogScreen(books, onBookClick, modifier)
                AppDestinations.PROFIL -> ProfileScreen(
                    activeLoans = activeLoans,
                    loanHistory = loanHistory,
                    onBookClick = onBookClick,
                    onReturnClick = onReturnClick,
                    modifier = modifier
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun LibraryHomePreview() {
    TUGASBARUTheme {
        LibraryHome(
            currentDestination = AppDestinations.BERANDA,
            onDestinationChange = {},
            books = SampleData.books,
            activeLoans = emptyList(),
            loanHistory = emptyList(),
            onBookClick = {},
            onReturnClick = {}
        )
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    BERANDA("Beranda", R.drawable.ic_home),
    KATALOG("Katalog", R.drawable.ic_book),
    PROFIL("Profil", R.drawable.ic_account_box),
}
