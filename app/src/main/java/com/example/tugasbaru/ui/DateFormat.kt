package com.example.tugasbaru.ui

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.ceil

private val indonesianDate = SimpleDateFormat("d MMM yyyy", Locale.forLanguageTag("id-ID"))

fun formatDate(millis: Long): String = indonesianDate.format(Date(millis))

/** Contoh: "Kembali 24 Okt 2026 (4 hari lagi)" atau "Terlambat 2 hari". */
fun dueDateLabel(dueDate: Long, now: Long = System.currentTimeMillis()): String {
    val daysLeft = ceil((dueDate - now).toDouble() / TimeUnit.DAYS.toMillis(1)).toInt()
    return when {
        daysLeft < 0 -> "Terlambat ${-daysLeft} hari"
        daysLeft == 0 -> "Kembali hari ini"
        else -> "Kembali ${formatDate(dueDate)} ($daysLeft hari lagi)"
    }
}
