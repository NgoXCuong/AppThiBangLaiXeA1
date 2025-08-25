package com.example.appthibanglaixea1.repository;

import android.app.Application;

import com.example.appthibanglaixea1.database.AppDatabase;
import com.example.appthibanglaixea1.database.dao.UserDao;
import com.example.appthibanglaixea1.database.entity.User;

public class UserRepository {

    private UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public void insertUser(User user, InsertCallback callback) {
        new Thread(() -> {
            try {
                long result = userDao.insertUser(user);
                if (callback != null) {
                    callback.onResult(result > 0, result > 0 ? "Đăng ký thành công!" : "Đăng ký thất bại!");
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onResult(false, "Lỗi: " + e.getMessage());
                }
            }
        }).start();
    }

    public void checkEmailExists(String email, ExistCallback callback) {
        new Thread(() -> {
            try {
                int count = userDao.countUsersByEmail(email);
                if (callback != null) {
                    callback.onResult(count > 0);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onResult(false);
                }
            }
        }).start();
    }

    public void checkPhoneExists(String phone, ExistCallback callback) {
        new Thread(() -> {
            try {
                int count = userDao.countUsersByPhone(phone);
                if (callback != null) {
                    callback.onResult(count > 0);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onResult(false);
                }
            }
        }).start();
    }

    public void loginUser(String email, String password, LoginCallback callback) {
        new Thread(() -> {
            try {
                User user = userDao.loginUser(email, password);
                if (callback != null) {
                    callback.onResult(user != null, user,
                            user != null ? "Đăng nhập thành công!" : "Email hoặc mật khẩu không đúng!");
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onResult(false, null, "Lỗi: " + e.getMessage());
                }
            }
        }).start();
    }

    public void getUserById(int userId, UserCallback callback) {
        new Thread(() -> {
            try {
                User user = userDao.getUserById(userId);
                if (callback != null) {
                    callback.onResult(user);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onResult(null);
                }
            }
        }).start();
    }

    public void updateUser(User user, UpdateCallback callback) {
        new Thread(() -> {
            try {
                userDao.updateUser(user);
                if (callback != null) {
                    callback.onResult(true, "Cập nhật thông tin thành công!");
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onResult(false, "Lỗi: " + e.getMessage());
                }
            }
        }).start();
    }

    // Callback Interfaces
    public interface InsertCallback {
        void onResult(boolean success, String message);
    }

    public interface ExistCallback {
        void onResult(boolean exists);
    }

    public interface LoginCallback {
        void onResult(boolean success, User user, String message);
    }

    public interface UserCallback {
        void onResult(User user);
    }

    public interface UpdateCallback {
        void onResult(boolean success, String message);
    }
}
