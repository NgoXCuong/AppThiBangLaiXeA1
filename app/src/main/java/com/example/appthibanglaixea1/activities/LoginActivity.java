package com.example.appthibanglaixea1.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appthibanglaixea1.R;
import com.example.appthibanglaixea1.database.entity.User;
import com.example.appthibanglaixea1.repository.UserRepository;
import com.example.appthibanglaixea1.utils.PasswordUtils;
import com.example.appthibanglaixea1.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {

    // UI Components
    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private ProgressBar progressBar;
    private TextView tvForgotPassword;
    private CheckBox cbRememberMe;

    // Repository
    private UserRepository userRepository;

    // SharedPreferences for remember login
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "login_pref";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initRepository();
        initSharedPreferences();
        setupClickListeners();
        setupTextWatchers();
        loadSavedCredentials();
        checkForRegisteredEmail();

        // 🔹 Chỉ auto-login nếu remember me được bật
        autoLoginIfRemembered();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
    }

    private void initRepository() {
        userRepository = new UserRepository(getApplication());
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> attemptLogin());

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        tvForgotPassword.setOnClickListener(v -> showForgotPasswordDialog());
    }

    private void setupTextWatchers() {
        etEmail.addTextChangedListener(createErrorClearTextWatcher(etEmail));
        etPassword.addTextChangedListener(createErrorClearTextWatcher(etPassword));
    }

    private TextWatcher createErrorClearTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setError(null);
            }
            @Override public void afterTextChanged(Editable s) {}
        };
    }

    private void loadSavedCredentials() {
        boolean remember = sharedPreferences.getBoolean(PREF_REMEMBER, false);
        if (remember) {
            String savedEmail = sharedPreferences.getString(PREF_EMAIL, "");
            etEmail.setText(savedEmail);
            cbRememberMe.setChecked(true);
        }
    }

    private void checkForRegisteredEmail() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("registered_email")) {
            String registeredEmail = intent.getStringExtra("registered_email");
            etEmail.setText(registeredEmail);
            etPassword.requestFocus();
        }
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim().toLowerCase();
        String password = etPassword.getText().toString();

        clearErrors();

        if (!validateInput(email, password)) {
            return;
        }

        showProgress(true);
        performLogin(email, password);
    }

    private void clearErrors() {
        etEmail.setError(null);
        etPassword.setError(null);
    }

    private boolean validateInput(String email, String password) {
        boolean isValid = true;
        View focusView = null;

        if (password.isEmpty()) {
            etPassword.setError("Vui lòng nhập mật khẩu");
            focusView = etPassword;
            isValid = false;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Email không hợp lệ");
            focusView = etEmail;
            isValid = false;
        }

        if (!isValid && focusView != null) {
            focusView.requestFocus();
        }

        return isValid;
    }

    private void performLogin(String email, String password) {
        userRepository.getUserByEmail(email, user -> {
            runOnUiThread(() -> {
                showProgress(false);
                if (user != null) {
                    if (PasswordUtils.verifyPassword(password, user.getPassword())) {
                        handleLoginSuccess(user);
                    } else {
                        etPassword.setError("Mật khẩu không chính xác");
                        etPassword.requestFocus();
                        etPassword.selectAll();
                    }
                } else {
                    etEmail.setError("Email chưa được đăng ký");
                    etEmail.requestFocus();
                }
            });
        });
    }

    private void handleLoginSuccess(User user) {
        saveRememberMePreferences();
        saveCurrentUser(user);

        Toast.makeText(this, "Đăng nhập thành công! Chào mừng " + user.getFullName(),
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void saveRememberMePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (cbRememberMe.isChecked()) {
            editor.putBoolean(PREF_REMEMBER, true);
            editor.putString(PREF_EMAIL, etEmail.getText().toString().trim());
        } else {
            editor.putBoolean(PREF_REMEMBER, false);
            editor.remove(PREF_EMAIL);
        }
        editor.apply();
    }

    private void saveCurrentUser(User user) {
        SharedPreferences userPref = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.putInt("user_id", user.getId());
        editor.putString("user_name", user.getFullName());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_phone", user.getPhone());
        editor.putLong("login_time", System.currentTimeMillis());
        editor.apply();
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!show);
        btnLogin.setText(show ? "Đang đăng nhập..." : "Đăng nhập");
        etEmail.setEnabled(!show);
        etPassword.setEnabled(!show);
        cbRememberMe.setEnabled(!show);
        btnRegister.setEnabled(!show);
        tvForgotPassword.setEnabled(!show);
    }

    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quên mật khẩu");

        final EditText input = new EditText(this);
        input.setHint("Nhập email của bạn");
        input.setText(etEmail.getText().toString().trim());
        builder.setView(input);

        builder.setPositiveButton("Gửi yêu cầu", (dialog, which) -> {
            String email = input.getText().toString().trim().toLowerCase();
            if (ValidationUtils.isValidEmail(email)) {
                handleForgotPassword(email);
            } else {
                Toast.makeText(LoginActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void handleForgotPassword(String email) {
        userRepository.getUserByEmail(email, user -> {
            runOnUiThread(() -> {
                if (user != null) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Yêu cầu đặt lại mật khẩu")
                            .setMessage("Hướng dẫn đặt lại mật khẩu đã được gửi đến email: " + email)
                            .setPositiveButton("Đã hiểu", null)
                            .show();
                } else {
                    Toast.makeText(LoginActivity.this, "Email chưa được đăng ký trong hệ thống",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences userPref = getSharedPreferences("user_session", MODE_PRIVATE);
        return userPref.contains("user_id");
    }

    public static void logoutUser(android.content.Context context) {
        SharedPreferences userPref = context.getSharedPreferences("user_session", MODE_PRIVATE);
        userPref.edit().clear().apply();
    }

    /**
     * 🔹 Chỉ auto login khi Remember Me được bật và user đã đăng nhập trước đó
     */
    private void autoLoginIfRemembered() {
        boolean remember = sharedPreferences.getBoolean(PREF_REMEMBER, false);
        if (remember && isUserLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

