package com.fieldservice.app.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.fieldservice.app.R;
import com.fieldservice.app.data.AppContainer;
import com.fieldservice.app.data.SettingsPrefs;
import com.fieldservice.app.databinding.FragmentProfileBinding;
import com.fieldservice.app.domain.model.Technician;
import com.fieldservice.app.domain.repository.AuthRepository;
import com.fieldservice.app.presentation.login.LoginActivity;

/** Aba de perfil do técnico logado, com a ação de logout. */
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AuthRepository authRepository = AppContainer.getAuthRepository();
        Technician technician = authRepository.getLoggedInTechnician();
        if (technician != null) {
            binding.textName.setText(technician.getName());
            binding.textEmail.setText(technician.getEmail());
            binding.textEmailInfo.setText(technician.getEmail());
            binding.textInitials.setText(initialsOf(technician.getName()));
        }

        binding.rowNotifications.setOnClickListener(v -> showNotificationsDialog());
        binding.rowTheme.setOnClickListener(v -> showThemeDialog());
        binding.rowAbout.setOnClickListener(v -> showAboutDialog());

        binding.buttonLogout.setOnClickListener(v -> {
            authRepository.logout();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    /** Seletor de tema: Escuro (padrão), Claro, Super Dark. Aplica recriando a tela. */
    private void showThemeDialog() {
        SettingsPrefs.ThemeMode current = SettingsPrefs.getThemeMode(requireContext());
        final SettingsPrefs.ThemeMode[] modes = {
                SettingsPrefs.ThemeMode.DARK,
                SettingsPrefs.ThemeMode.LIGHT,
                SettingsPrefs.ThemeMode.SUPER_DARK
        };
        String[] labels = {
                getString(R.string.theme_dark),
                getString(R.string.theme_light),
                getString(R.string.theme_super_dark)
        };
        int checked = current == SettingsPrefs.ThemeMode.LIGHT ? 1
                : current == SettingsPrefs.ThemeMode.SUPER_DARK ? 2 : 0;

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.profile_settings_theme)
                .setSingleChoiceItems(labels, checked, (dialog, which) -> {
                    SettingsPrefs.ThemeMode chosen = modes[which];
                    dialog.dismiss();
                    if (chosen != current) {
                        SettingsPrefs.setThemeMode(requireContext(), chosen);
                        com.fieldservice.app.FieldServiceApp.applyNightMode(requireContext());
                        requireActivity().recreate();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    /** Preferências de notificação: novo chamado e chamado com mais de 2h de atuação. */
    private void showNotificationsDialog() {
        String[] labels = {
                getString(R.string.notif_new_ticket),
                getString(R.string.notif_ticket_2h)
        };
        final boolean[] checked = {
                SettingsPrefs.isNotifyNewTicket(requireContext()),
                SettingsPrefs.isNotifyTicket2h(requireContext())
        };
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.profile_settings_notifications)
                .setMultiChoiceItems(labels, checked, (dialog, which, isChecked) -> checked[which] = isChecked)
                .setPositiveButton(R.string.action_save, (dialog, which) -> {
                    SettingsPrefs.setNotifyNewTicket(requireContext(), checked[0]);
                    SettingsPrefs.setNotifyTicket2h(requireContext(), checked[1]);
                    Toast.makeText(requireContext(), R.string.settings_saved, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    /** Sobre o app: nome, versão e descrição. */
    private void showAboutDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.app_name)
                .setMessage(getString(R.string.about_message))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    /** Extrai as iniciais do técnico (ex.: "João Silva" -> "JS") para o avatar. */
    private String initialsOf(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "?";
        }
        String[] parts = name.trim().split("\\s+");
        String first = parts[0].substring(0, 1);
        String last = parts.length > 1
                ? parts[parts.length - 1].substring(0, 1)
                : "";
        return (first + last).toUpperCase();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
