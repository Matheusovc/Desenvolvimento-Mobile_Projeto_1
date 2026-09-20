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

import com.fieldservice.app.R;
import com.fieldservice.app.data.AppContainer;
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

        // Configurações ainda não implementadas: sinalizam como funcionalidade futura.
        View.OnClickListener soon = v ->
                Toast.makeText(requireContext(), R.string.profile_soon, Toast.LENGTH_SHORT).show();
        binding.rowNotifications.setOnClickListener(soon);
        binding.rowTheme.setOnClickListener(soon);
        binding.rowAbout.setOnClickListener(soon);

        binding.buttonLogout.setOnClickListener(v -> {
            authRepository.logout();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
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
