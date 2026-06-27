package com.example.generalstoreapp.ui.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.services.handlingservices.ApiResult;
import com.example.generalstoreapp.utils.SharedPreferencesUtils;
import com.example.generalstoreapp.utils.TokenProvider;
import com.example.generalstoreapp.viewmodel.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextUserName;
    private TextInputEditText editTextPassword;
    private MaterialButton buttonLogin;
    private TextView textViewForgotPassword;
    private TextView textViewRegistration;
    private ProgressBar progressBar;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        // Check if user is already logged in
        if (SharedPreferencesUtils.getLoginDataPreferences(this) != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        // Handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, windowInsets) -> {
            androidx.core.graphics.Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        editTextUserName = findViewById(R.id.edit_login_username);
        editTextPassword = findViewById(R.id.edit_login_password);
        textViewForgotPassword = findViewById(R.id.text_login_forgot_password);
        textViewRegistration = findViewById(R.id.text_login_registration);
        buttonLogin = findViewById(R.id.button_login);
        progressBar = findViewById(R.id.progressBar_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.init(this);

        observeViewModel();

        buttonLogin.setOnClickListener(v -> {
            String username = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                viewModel.login(username, password);
            } else {
                Toast.makeText(this, getString(R.string.all_fields_required), Toast.LENGTH_SHORT).show();
            }
        });

        textViewForgotPassword.setOnClickListener(v -> 
            Toast.makeText(this, "Forgot Password Operation In-Process", Toast.LENGTH_SHORT).show()
        );

        textViewRegistration.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(this, loading -> {
            progressBar.setVisibility(loading ? VISIBLE : GONE);
            buttonLogin.setEnabled(!loading);
        });

        viewModel.getLoginResult().observe(this, result -> {
            if (result.status == ApiResult.Status.SUCCESS) {
                TokenProvider.get(this).saveTokens(
                        result.data.getAccessToken(),
                        result.data.getRefreshToken());

                SharedPreferencesUtils.setLoginDataPreferences(this, result.data);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                String message = !TextUtils.isEmpty(result.message) ? result.message : "Login failed";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
