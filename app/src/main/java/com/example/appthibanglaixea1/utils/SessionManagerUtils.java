package com.example.appthibanglaixea1.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagerUtils {
    private static final String PREF_NAME = "driving_license_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_LOGIN_TIME = "login_time";
    private static final String KEY_REMEMBER_ME = "remember_me";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManagerUtils(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Tạo session đăng nhập
     */
    public void createLoginSession(int userId, String userName, String email, String phone, boolean rememberMe) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PHONE, phone);
        editor.putLong(KEY_LOGIN_TIME, System.currentTimeMillis());
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
        editor.apply();
    }

    /**
     * Kiểm tra đã đăng nhập chưa
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Lấy User ID
     */
    public int getUserId() {
        return pref.getInt(KEY_USER_ID, -1);
    }

    /**
     * Lấy tên user
     */
    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

    /**
     * Lấy email user
     */
    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }

    /**
     * Lấy số điện thoại
     */
    public String getUserPhone() {
        return pref.getString(KEY_USER_PHONE, "");
    }

    /**
     * Lấy thời gian đăng nhập
     */
    public long getLoginTime() {
        return pref.getLong(KEY_LOGIN_TIME, 0);
    }

    /**
     * Kiểm tra Remember Me
     */
    public boolean isRememberMe() {
        return pref.getBoolean(KEY_REMEMBER_ME, false);
    }

    /**
     * Đăng xuất
     */
    public void logout() {
        editor.clear();
        editor.apply();
    }

    /**
     * Cập nhật thông tin user
     */
    public void updateUserInfo(String userName, String email, String phone) {
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PHONE, phone);
        editor.apply();
    }

    /**
     * Kiểm tra session còn hạn không (7 ngày)
     */
    public boolean isSessionValid() {
        if (!isLoggedIn()) return false;

        long loginTime = getLoginTime();
        long currentTime = System.currentTimeMillis();
        long sessionDuration = 7 * 24 * 60 * 60 * 1000L; // 7 ngày

        return (currentTime - loginTime) < sessionDuration;
    }

    /**
     * Force logout nếu session hết hạn
     */
    public void checkSessionExpiry() {
        if (isLoggedIn() && !isSessionValid() && !isRememberMe()) {
            logout();
        }
    }
}
