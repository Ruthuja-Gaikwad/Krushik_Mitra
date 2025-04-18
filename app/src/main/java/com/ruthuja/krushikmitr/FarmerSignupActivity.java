package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class FarmerSignupActivity extends AppCompatActivity {

    private EditText etSignupEmail, etSignupPassword;
    private Button btnSignupSubmit, btnBackToLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_signup);

        mAuth = FirebaseAuth.getInstance();

        etSignupEmail = findViewById(R.id.et_signup_email);
        etSignupPassword = findViewById(R.id.et_signup_password);
        btnSignupSubmit = findViewById(R.id.btn_signup_submit);
        btnBackToLogin = findViewById(R.id.btn_back_to_login);

        btnSignupSubmit.setOnClickListener(view -> handleSignup());
        btnBackToLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, FarmerLoginActivity.class));
            finish();
        });
    }

    private void handleSignup() {
        String email = etSignupEmail.getText().toString().trim();
        String password = etSignupPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etSignupEmail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            etSignupPassword.setError("Password must be at least 6 characters");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Account created! Please log in.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, FarmerLoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
