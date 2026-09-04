package com.fieldservice.app.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fieldservice.app.data.AppContainer;
import com.fieldservice.app.databinding.ActivityLoginBinding;
import com.fieldservice.app.presentation.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LoginViewModel(AppContainer.getAuthRepository());
            }
        }).get(LoginViewModel.class);

        setUpListeners();
        observeUiState();
    }

    private void setUpListeners() {
        binding.editEmail.addTextChangedListener(new SimpleTextWatcher(viewModel::onEmailChange));
        binding.editPassword.addTextChangedListener(new SimpleTextWatcher(viewModel::onPasswordChange));
        binding.buttonLogin.setOnClickListener(v -> viewModel.login());
    }

    private void observeUiState() {
        viewModel.getUiState().observe(this, state -> {
            boolean loading = state.isLoading();
            binding.buttonLogin.setEnabled(!loading);
            binding.progressLogin.setVisibility(loading ? View.VISIBLE : View.GONE);

            if (state.getErrorMessage() != null) {
                binding.textError.setVisibility(View.VISIBLE);
                binding.textError.setText(state.getErrorMessage());
            } else {
                binding.textError.setVisibility(View.GONE);
            }

            if (state.isLoginSuccessful()) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
        });
    }

    /** Adapta o callback simples de texto para a interface {@link TextWatcher} do Android. */
    private static final class SimpleTextWatcher implements TextWatcher {

        interface OnTextChanged {
            void onChanged(String text);
        }

        private final OnTextChanged onTextChanged;

        SimpleTextWatcher(OnTextChanged onTextChanged) {
            this.onTextChanged = onTextChanged;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextChanged.onChanged(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
