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

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.RegistrationRequest;
import com.example.generalstoreapp.repository.AuthRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText editTextFirstName;
    private TextInputEditText editTextLastName;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextMobile;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextCNFPassword;
    private TextView textViewAlreadyLogin;
    private MaterialButton buttonRegistration;
    private ProgressBar progressBar;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, windowInsets) -> {
            androidx.core.graphics.Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });
        
        initViews();
        
        authRepository = new AuthRepository(this);

        buttonRegistration.setOnClickListener(v -> {
            if (validateForm()) {
                saveDataFromServer();
            }
        });

        textViewAlreadyLogin.setOnClickListener(v -> finish());
    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBar_registration);
        editTextFirstName = findViewById(R.id.edit_registration_first_name);
        editTextLastName = findViewById(R.id.edit_registration_last_name);
        editTextEmail = findViewById(R.id.edit_registration_email);
        editTextMobile = findViewById(R.id.edit_registration_mobile);
        editTextPassword = findViewById(R.id.edit_registration_password);
        editTextCNFPassword = findViewById(R.id.edit_registration_cn_password);
        textViewAlreadyLogin = findViewById(R.id.text_registration_already_login);
        buttonRegistration = findViewById(R.id.button_registration);
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(editTextFirstName.getText()) ||
            TextUtils.isEmpty(editTextLastName.getText()) ||
            TextUtils.isEmpty(editTextEmail.getText()) ||
            TextUtils.isEmpty(editTextPassword.getText())) {
            Toast.makeText(this, getString(R.string.fill_required_data), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!editTextPassword.getText().toString().equals(editTextCNFPassword.getText().toString())) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void saveDataFromServer() {
        progressBar.setVisibility(VISIBLE);
        buttonRegistration.setEnabled(false);
        
        RegistrationRequest request = new RegistrationRequest();
        request.setOwnerName(editTextFirstName.getText().toString() + " " + editTextLastName.getText().toString());
        request.setEmail(editTextEmail.getText().toString());
        request.setPhone(editTextMobile.getText().toString());
        request.setPassword(editTextPassword.getText().toString());

        authRepository.register(request, result -> {
            progressBar.setVisibility(GONE);
            buttonRegistration.setEnabled(true);
            if (result.status == ApiResult.Status.SUCCESS) {
                Toast.makeText(this, getString(R.string.operation_successful), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                String message = !TextUtils.isEmpty(result.message) ? result.message : "Registration failed";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
