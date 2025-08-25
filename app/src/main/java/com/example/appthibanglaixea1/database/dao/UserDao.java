//package com.example.appthibanglaixea1.database.dao;
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.example.appthibanglaixea1.database.entity.User;
//
//@Dao
//public interface UserDao {
//
//    @Insert
//    long insertUser(User user);
//
//    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
//    User getUserByEmail(String email);
//
//    @Query("SELECT * FROM users WHERE phone = :phone LIMIT 1")
//    User getUserByPhone(String phone);
//
//    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
//    int countUsersByEmail(String email);
//
//    @Query("SELECT COUNT(*) FROM users WHERE phone = :phone")
//    int countUsersByPhone(String phone);
//
//    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
//    User loginUser(String email, String password);
//
//    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
//    User getUserById(int userId);
//
//    @Update
//    void updateUser(User user);
//
//    @Delete
//    void deleteUser(User user);
//
//    @Query("DELETE FROM users WHERE id = :userId")
//    void deleteUserById(int userId);
//
//    @Query("SELECT COUNT(*) FROM users")
//    int getTotalUsers();
//}
package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    // Insert user - trả về long (ID của record mới)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    // Update user
    @Update
    void updateUser(User user);

    // Delete user
    @Delete
    int delete(User user);

    // Get all users
    @Query("SELECT * FROM users ORDER BY created_at DESC")
    LiveData<List<User>> getAllUsers();

    // Get user by email
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    // Get user by phone
    @Query("SELECT * FROM users WHERE phone = :phone LIMIT 1")
    User getUserByPhone(String phone);

    // Get user by ID
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getUserById(int id);

    // Count users by email (for checking if email exists)
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int countUsersByEmail(String email);

    // Count users by phone (for checking if phone exists)
    @Query("SELECT COUNT(*) FROM users WHERE phone = :phone")
    int countUsersByPhone(String phone);

    // Login user - get user by email and password
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User loginUser(String email, String password);

    // Get total user count
    @Query("SELECT COUNT(*) FROM users")
    int getUserCount();

    // Delete all users
    @Query("DELETE FROM users")
    void deleteAllUsers();

    // Search users
    @Query("SELECT * FROM users WHERE email LIKE :searchQuery OR full_name LIKE :searchQuery")
    LiveData<List<User>> searchUsers(String searchQuery);

    // Additional methods for compatibility with existing code
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    boolean isEmailExists(String email);

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE phone = :phone)")
    boolean isPhoneExists(String phone);
}