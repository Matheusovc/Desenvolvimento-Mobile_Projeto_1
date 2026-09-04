package com.fieldservice.app.ui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.fieldservice.app.R;
import com.fieldservice.app.domain.model.TicketStatus;

/** Selo de status do chamado, com rótulo textual (nunca só uma cor). */
public final class StatusBadge {

    private static final int BACKGROUND_ALPHA = (int) (0.16f * 255);

    private StatusBadge() {
    }

    public static String label(TicketStatus status) {
        switch (status) {
            case OPEN:
                return "Aberto";
            case ASSIGNED:
                return "Atribuído";
            case ACCEPTED:
                return "Aceito";
            case TRAVELING:
                return "A caminho";
            case ON_SITE:
                return "No local";
            case IN_PROGRESS:
                return "Em atendimento";
            case WAITING_CONFIRMATION:
                return "Aguardando confirmação";
            case COMPLETED:
                return "Concluído";
            case CANCELLED:
                return "Cancelado";
            default:
                return "";
        }
    }

    private static int colorRes(TicketStatus status) {
        switch (status) {
            case OPEN:
            case ASSIGNED:
                return R.color.status_neutral;
            case ACCEPTED:
            case TRAVELING:
            case ON_SITE:
            case IN_PROGRESS:
            case WAITING_CONFIRMATION:
                return R.color.status_in_progress;
            case COMPLETED:
                return R.color.status_completed;
            case CANCELLED:
                return R.color.status_cancelled;
            default:
                return R.color.color_on_surface;
        }
    }

    public static void bind(TextView view, TicketStatus status) {
        Context context = view.getContext();
        int color = ContextCompat.getColor(context, colorRes(status));

        view.setText(label(status));
        view.setTextColor(color);

        Drawable background = ContextCompat.getDrawable(context, R.drawable.bg_badge);
        if (background != null) {
            background = background.mutate();
            background.setTint(ColorUtils.setAlphaComponent(color, BACKGROUND_ALPHA));
            view.setBackground(background);
        }
    }
}
