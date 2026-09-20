package com.fieldservice.app;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.fieldservice.app.data.SettingsPrefs;

/**
 * Application do FieldService. Ajusta o modo noturno global conforme o tema escolhido,
 * para que os componentes Material (diálogos, switches, etc.) fiquem consistentes com o app.
 */
public class FieldServiceApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        applyNightMode(this);
    }

    /** Claro -> modo diurno; Dark/Super Dark -> modo noturno. */
    public static void applyNightMode(Context context) {
        AppCompatDelegate.setDefaultNightMode(
                SettingsPrefs.isLight(context)
                        ? AppCompatDelegate.MODE_NIGHT_NO
                        : AppCompatDelegate.MODE_NIGHT_YES);
    }
}
