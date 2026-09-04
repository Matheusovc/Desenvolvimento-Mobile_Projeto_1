package com.fieldservice.app.presentation.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fieldservice.app.data.AppContainer;
import com.fieldservice.app.databinding.ActivityProfileBinding;
import com.fieldservice.app.domain.model.Technician;
import com.fieldservice.app.domain.repository.AuthRepository;
import com.fieldservice.app.presentation.login.LoginActivity;

/** Perfil do técnico logado, com a ação de logout. */
public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarProfile.setNavigationOnClickListener(v -> finish());

        AuthRepository authRepository = AppContainer.getAuthRepository();
        Technician technician = authRepository.getLoggedInTechnician();
        if (technician != null) {
            binding.textName.setText(technician.getName());
            binding.textEmail.setText(technician.getEmail());
        }

        binding.buttonLogout.setOnClickListener(v -> {
            authRepository.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });
    }
}
