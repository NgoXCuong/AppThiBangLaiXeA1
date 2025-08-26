package com.example.appthibanglaixea1.activities;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    // UI Components
    private EditText etFullName, etEmail, etPhone, etPassword, etConfirmPassword;
    private Button btnRegister, btnBackToLogin;
    private ProgressBar progressBar;
    private TextView tvPasswordStrength, tvTerms;
    private CheckBox cbTerms;

    // Repository
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initRepository();
        setupClickListeners();
        setupTextWatchers();
    }

    /**
     * Khởi tạo các view
     */
    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        progressBar = findViewById(R.id.progressBar);
        tvPasswordStrength = findViewById(R.id.tvPasswordStrength);
        tvTerms = findViewById(R.id.tvTerms);
        cbTerms = findViewById(R.id.cbTerms);
    }

    /**
     * Khởi tạo repository
     */
    private void initRepository() {
        userRepository = new UserRepository(getApplication());
    }

    /**
     * Thiết lập các sự kiện click
     */
    private void setupClickListeners() {
        btnRegister.setOnClickListener(v -> attemptRegister());

        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        tvTerms.setOnClickListener(v -> showTermsDialog());
    }

    /**
     * Thiết lập TextWatcher cho các EditText
     */
    private void setupTextWatchers() {
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Xóa lỗi khi người dùng bắt đầu nhập
        etFullName.addTextChangedListener(createErrorClearTextWatcher(etFullName));
        etEmail.addTextChangedListener(createErrorClearTextWatcher(etEmail));
        etPhone.addTextChangedListener(createErrorClearTextWatcher(etPhone));
        etPassword.addTextChangedListener(createErrorClearTextWatcher(etPassword));
        etConfirmPassword.addTextChangedListener(createErrorClearTextWatcher(etConfirmPassword));
    }

    /**
     * Tạo TextWatcher để xóa error message
     */
    private TextWatcher createErrorClearTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    /**
     * Cập nhật hiển thị độ mạnh mật khẩu
     */
    private void updatePasswordStrength(String password) {
        String strength = ValidationUtils.getPasswordStrength(password);
        tvPasswordStrength.setText("Độ mạnh mật khẩu: " + strength);

        int color;
        switch (strength) {
            case "Mạnh":
                color = getResources().getColor(android.R.color.holo_green_dark);
                break;
            case "Trung bình":
                color = getResources().getColor(android.R.color.holo_orange_dark);
                break;
            default:
                color = getResources().getColor(android.R.color.holo_red_dark);
                break;
        }
        tvPasswordStrength.setTextColor(color);
    }

    /**
     * Thực hiện đăng ký
     */
    private void attemptRegister() {
        // Lấy dữ liệu từ form
        String fullName = ValidationUtils.cleanString(etFullName.getText().toString());
        String email = etEmail.getText().toString().trim().toLowerCase();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // Xóa các lỗi cũ
        clearErrors();

        // Validate dữ liệu
        if (!validateInput(fullName, email, phone, password, confirmPassword)) {
            return;
        }

        // Kiểm tra điều khoản
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Vui lòng đồng ý với điều khoản sử dụng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị loading
        showProgress(true);

        // Kiểm tra email và phone đã tồn tại
        checkUserExists(fullName, email, phone, password);
    }

    /**
     * Xóa tất cả error message
     */
    private void clearErrors() {
        etFullName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);
    }

    /**
     * Validate input data
     */
    private boolean validateInput(String fullName, String email, String phone,
                                  String password, String confirmPassword) {
        boolean isValid = true;
        View focusView = null;

        // Validate confirm password
        if (!ValidationUtils.isPasswordMatch(password, confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            focusView = etConfirmPassword;
            isValid = false;
        }

        // Validate password
        if (!ValidationUtils.isValidPassword(password)) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            focusView = etPassword;
            isValid = false;
        }

        // Validate phone
        if (!ValidationUtils.isValidPhone(phone)) {
            etPhone.setError("Số điện thoại không hợp lệ (VD: 0901234567)");
            focusView = etPhone;
            isValid = false;
        }

        // Validate email
        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Email không hợp lệ");
            focusView = etEmail;
            isValid = false;
        }

        // Validate full name
        if (!ValidationUtils.isValidFullName(fullName)) {
            etFullName.setError("Họ tên phải có ít nhất 2 ký tự");
            focusView = etFullName;
            isValid = false;
        }

        if (!isValid && focusView != null) {
            focusView.requestFocus();
        }

        return isValid;
    }

    /**
     * Kiểm tra user đã tồn tại chưa
     */
    private void checkUserExists(String fullName, String email, String phone, String password) {
        userRepository.checkEmailExists(email, emailExists -> {
            if (emailExists) {
                runOnUiThread(() -> {
                    showProgress(false);
                    etEmail.setError("Email đã được sử dụng");
                    etEmail.requestFocus();
                });
            } else {
                userRepository.checkPhoneExists(phone, phoneExists -> {
                    if (phoneExists) {
                        runOnUiThread(() -> {
                            showProgress(false);
                            etPhone.setError("Số điện thoại đã được sử dụng");
                            etPhone.requestFocus();
                        });
                    } else {
                        registerUser(fullName, email, phone, password);
                    }
                });
            }
        });
    }

    /**
     * Đăng ký user mới
     */
    private void registerUser(String fullName, String email, String phone, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password);
        User user = new User(fullName, email, phone, hashedPassword);

        userRepository.insertUser(user, (success, message) -> {
            runOnUiThread(() -> {
                showProgress(false);
                if (success) {
                    showSuccessDialog(email);
                } else {
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    /**
     * Hiển thị dialog thành công
     */
    private void showSuccessDialog(String email) {
        new AlertDialog.Builder(this)
                .setTitle("Đăng ký thành công!")
                .setMessage("Tài khoản của bạn đã được tạo thành công.\nBạn có thể đăng nhập bằng email: " + email)
                .setPositiveButton("Đăng nhập ngay", (dialog, which) -> {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("registered_email", email);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Để sau", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    /**
     * Hiển thị/ẩn progress bar
     */
    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnRegister.setEnabled(!show);
        btnRegister.setText(show ? "Đang đăng ký..." : "Đăng ký");

        // Disable tất cả input khi đang xử lý
        etFullName.setEnabled(!show);
        etEmail.setEnabled(!show);
        etPhone.setEnabled(!show);
        etPassword.setEnabled(!show);
        etConfirmPassword.setEnabled(!show);
        cbTerms.setEnabled(!show);
        btnBackToLogin.setEnabled(!show);
    }

    /**
     * Hiển thị dialog điều khoản sử dụng
     */
    private void showTermsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Điều khoản sử dụng ứng dụng");
        builder.setMessage(getTermsContent());
        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            cbTerms.setChecked(true);
            dialog.dismiss();
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    /**
     * Nội dung điều khoản sử dụng
     */
    private String getTermsContent() {
        return "ĐIỀU KHOẢN SỬ DỤNG ỨNG DỤNG LUYỆN THI LÝ THUYẾT LÁI XE A1\n\n" +
                "1. GIỚI THIỆU\n" +
                "Ứng dụng được phát triển nhằm hỗ trợ học viên ôn tập và luyện thi lý thuyết lái xe hạng A1 theo quy định của Bộ GTVT.\n\n" +
                "2. QUYỀN VÀ NGHĨA VỤ NGƯỜI DÙNG\n" +
                "- Sử dụng ứng dụng đúng mục đích học tập\n" +
                "- Cung cấp thông tin chính xác khi đăng ký\n" +
                "- Không sao chép, phân phối nội dung ứng dụng\n" +
                "- Báo cáo lỗi hoặc sai sót cho nhà phát triển\n\n" +
                "3. CHÍNH SÁCH BẢO MẬT\n" +
                "- Thông tin cá nhân được bảo mật tuyệt đối\n" +
                "- Không chia sẻ thông tin với bên thứ ba\n" +
                "- Dữ liệu học tập được lưu trữ an toàn\n\n" +
                "4. TRÁCH NHIỆM CỦA NHÀ PHÁT TRIỂN\n" +
                "- Cập nhật nội dung theo quy định mới nhất\n" +
                "- Bảo trì và nâng cấp ứng dụng\n" +
                "- Hỗ trợ người dùng khi gặp vấn đề\n\n" +
                "5. ĐIỀU KHOẢN KHÁC\n" +
                "- Điều khoản có thể được cập nhật khi cần thiết\n" +
                "- Người dùng sẽ được thông báo về các thay đổi quan trọng\n" +
                "- Mọi tranh chấp sẽ được giải quyết theo pháp luật Việt Nam\n\n" +
                "Bằng việc sử dụng ứng dụng, bạn đã đồng ý với tất cả điều khoản trên.";
    }

//    @Override
//    public void onBackPressed() {
//        if (progressBar.getVisibility() == View.VISIBLE) {
//            // Không cho phép back khi đang xử lý
//            Toast.makeText(this, "Vui lòng chờ quá trình đăng ký hoàn tất", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        super.onBackPressed();
//    }
}