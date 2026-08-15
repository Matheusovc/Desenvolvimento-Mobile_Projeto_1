package com.fieldservice.app.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Centraliza o formato de data exibido na UI, evitando padrões repetidos pelas telas. */
object DateFormatter {
    // Locale.of() (substituto não-depreciado) exige API 31+; o construtor abaixo
    // é o único caminho compatível com o minSdk 24 deste projeto.
    @Suppress("DEPRECATION")
    private val displayFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))

    fun format(date: Date): String = displayFormat.format(date)
}
