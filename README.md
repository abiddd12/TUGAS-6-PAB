# Tugas Pertemuan 6 - Aplikasi Perpustakaan dengan Basis Template

- **Nama:** Muhammad Syuja Al Abid
- **NIM:** 102042330012

Aplikasi perpustakaan sederhana berbasis Android (Kotlin + Jetpack Compose) untuk mencari buku, melihat detail, meminjam, dan mengembalikan buku.

## Template Awal

Project dibuat dari template Android Studio **Navigation UI Activity** (Jetpack Compose). Template ini berisi `NavigationSuiteScaffold` dengan 3 menu bawaan: Home, Favorites, dan Profile. Setiap menu hanya menampilkan teks sapaan.

## Modifikasi dari Template

1. Menu bawaan Home / Favorites / Profile diganti menjadi **Beranda / Katalog / Profil**. Icon Favorites juga diganti dengan icon buku (`ic_book`).
2. Teks sapaan di tiap menu diganti dengan halaman lengkap: `HomeScreen`, `CatalogScreen`, dan `ProfileScreen`.
3. Menambahkan halaman **Detail Buku** (`BookDetailScreen`) berisi sampul, sinopsis, kategori, tahun, jumlah halaman, dan tombol pinjam/kembalikan.
4. Menambahkan database lokal **Room** (tabel `books` dan `loans`). Database diisi otomatis dengan 10 buku contoh saat aplikasi pertama kali dijalankan.
5. Menambahkan **ViewModel** (`LibraryViewModel`) dengan `StateFlow`, sehingga data tampil secara reaktif.
6. Menambahkan aturan peminjaman: lama pinjam 14 hari dan maksimal 3 buku dipinjam sekaligus.
7. Menambahkan komponen UI sendiri: sampul buku berwarna, kartu statistik, chip status ketersediaan, dan item daftar buku.
8. Navigasi tombol Back: dari halaman detail kembali ke menu sebelumnya, dari Katalog/Profil kembali ke Beranda, dan dari Beranda keluar aplikasi. Isi pencarian dan posisi scroll tiap menu tetap tersimpan.
9. Mengganti nama aplikasi menjadi **Perpustakaan**.

## Menu Navigasi

| Menu | Fitur |
|------|-------|
| **Beranda** | Sapaan, statistik (jumlah buku dipinjam, koleksi, dan tersedia), daftar **Buku Populer** dan **Baru Ditambahkan**, serta tombol "Lihat semua" menuju Katalog. |
| **Katalog** | Pencarian buku berdasarkan judul atau penulis, filter kategori dengan chip, dan daftar buku dengan status ketersediaan. |
| **Profil** | Info anggota, daftar buku yang **sedang dipinjam** beserta batas waktu dan tombol Kembalikan, serta **riwayat peminjaman**. |

Buku di menu mana pun bisa diketuk untuk membuka halaman Detail Buku.

## Cara Menjalankan

1. Clone repository:
   ```bash
   git clone https://github.com/abiddd12/TUGAS-6-PAB.git
   ```
2. Buka folder project di **Android Studio**, lalu tunggu Gradle Sync selesai.
3. Jalankan di emulator atau HP Android (minimal Android 7.0 / API 24) dengan tombol **Run ▶**, atau lewat terminal:
   ```bash
   ./gradlew installDebug
   ```
