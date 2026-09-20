package com.fieldservice.app.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.fieldservice.app.R;

/**
 * Preferências locais do app (tema e notificações), persistidas em SharedPreferences.
 * Simples e sem dependências externas — coerente com a base atual do projeto.
 */
public final class SettingsPrefs {

    /** Temas disponíveis para o app. */
    public enum ThemeMode {
        DARK, LIGHT, SUPER_DARK
    }

    private static final String PREFS = "fs_settings";
    private static final String KEY_THEME = "theme_mode";
    private static final String KEY_NOTIF_NEW = "notif_new_ticket";
    private static final String KEY_NOTIF_2H = "notif_ticket_2h";

    private SettingsPrefs() {
    }

    private static SharedPreferences prefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    // ---- Tema ----

    public static ThemeMode getThemeMode(Context context) {
        String name = prefs(context).getString(KEY_THEME, ThemeMode.DARK.name());
        try {
            return ThemeMode.valueOf(name);
        } catch (IllegalArgumentException e) {
            return ThemeMode.DARK;
        }
    }

    public static void setThemeMode(Context context, ThemeMode mode) {
        prefs(context).edit().putString(KEY_THEME, mode.name()).apply();
    }

    /** Estilo de tema correspondente ao modo salvo, aplicado nas Activities. */
    public static int themeStyleRes(Context context) {
        switch (getThemeMode(context)) {
            case LIGHT:
                return R.style.Theme_FieldService_Light;
            case SUPER_DARK:
                return R.style.Theme_FieldService_SuperDark;
            case DARK:
            default:
                return R.style.Theme_FieldService;
        }
    }

    public static boolean isLight(Context context) {
        return getThemeMode(context) == ThemeMode.LIGHT;
    }

    // ---- Notificações ----

    public static boolean isNotifyNewTicket(Context context) {
        return prefs(context).getBoolean(KEY_NOTIF_NEW, true);
    }

    public static void setNotifyNewTicket(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_NOTIF_NEW, enabled).apply();
    }

    public static boolean isNotifyTicket2h(Context context) {
        return prefs(context).getBoolean(KEY_NOTIF_2H, true);
    }

    public static void setNotifyTicket2h(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_NOTIF_2H, enabled).apply();
    }
}
