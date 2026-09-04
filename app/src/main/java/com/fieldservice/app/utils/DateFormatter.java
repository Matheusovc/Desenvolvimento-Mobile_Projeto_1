package com.fieldservice.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Centraliza o formato de data exibido na UI, evitando padrões repetidos pelas telas. */
public final class DateFormatter {

    private static final SimpleDateFormat DISPLAY_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

    private DateFormatter() {
    }

    public static String format(Date date) {
        return DISPLAY_FORMAT.format(date);
    }
}
