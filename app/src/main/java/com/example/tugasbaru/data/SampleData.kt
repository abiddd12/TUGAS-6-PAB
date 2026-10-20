package com.example.tugasbaru.data

/** Data awal yang dimasukkan ke database saat aplikasi pertama kali dibuka. */
object SampleData {
    val categories = listOf("Semua", "Novel", "Sains", "Sejarah", "Teknologi", "Agama")

    val books = listOf(
        Book(
            1, "Laskar Pelangi", "Andrea Hirata", "Novel", 2005, 529,
            "Kisah sepuluh anak dari Belitung yang berjuang menempuh pendidikan di sekolah sederhana bersama guru-guru yang penuh dedikasi.",
            0xFF5C6BC0
        ),
        Book(
            2, "Bumi Manusia", "Pramoedya A. Toer", "Novel", 1980, 535,
            "Kisah Minke, pemuda pribumi di masa kolonial Belanda, dan cintanya kepada Annelies. Bagian pertama dari Tetralogi Buru.",
            0xFF8D6E63, available = false
        ),
        Book(
            3, "Sapiens", "Yuval Noah Harari", "Sejarah", 2011, 498,
            "Riwayat singkat umat manusia, dari revolusi kognitif hingga revolusi ilmiah, dan bagaimana kita menguasai dunia.",
            0xFFEF6C00
        ),
        Book(
            4, "A Brief History of Time", "Stephen Hawking", "Sains", 1988, 256,
            "Penjelasan populer tentang alam semesta: dari big bang, lubang hitam, hingga sifat waktu.",
            0xFF26A69A
        ),
        Book(
            5, "Clean Code", "Robert C. Martin", "Teknologi", 2008, 464,
            "Panduan menulis kode yang mudah dibaca, dirawat, dan diuji, lengkap dengan studi kasus.",
            0xFF42A5F5, available = false
        ),
        Book(
            6, "Negeri 5 Menara", "Ahmad Fuadi", "Novel", 2009, 423,
            "Perjalanan Alif dan lima sahabatnya di pondok pesantren dengan semangat \"man jadda wajada\".",
            0xFFAB47BC
        ),
        Book(
            7, "Sejarah Indonesia Modern", "M.C. Ricklefs", "Sejarah", 1981, 720,
            "Sejarah Indonesia sejak kedatangan Islam sekitar tahun 1200 hingga masa modern.",
            0xFF78909C
        ),
        Book(
            8, "Kosmos", "Carl Sagan", "Sains", 1980, 396,
            "Penjelajahan tentang alam semesta, sejarah ilmu pengetahuan, dan tempat manusia di dalamnya.",
            0xFF283593
        ),
        Book(
            9, "Kotlin in Action", "Dmitry Jemerov", "Teknologi", 2017, 360,
            "Panduan bahasa Kotlin untuk pengembang, dari dasar hingga fitur lanjutan seperti coroutine dan DSL.",
            0xFF7E57C2
        ),
        Book(
            10, "Fiqih Sunnah", "Sayyid Sabiq", "Agama", 1946, 612,
            "Kitab fiqih yang membahas ibadah dan muamalah berdasarkan Al-Qur'an dan Sunnah.",
            0xFF2E7D32
        ),
    )
}
