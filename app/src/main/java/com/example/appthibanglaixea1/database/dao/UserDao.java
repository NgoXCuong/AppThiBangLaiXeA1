package com.example.appthibanglaixea1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appthibanglaixea1.database.entity.User;

@Dao
public interface UserDao {

    @Insert
    long insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE phone = :phone LIMIT 1")
    User getUserByPhone(String phone);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int countUsersByEmail(String email);

    @Query("SELECT COUNT(*) FROM users WHERE phone = :phone")
    int countUsersByPhone(String phone);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User loginUser(String email, String password);

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    User getUserById(int userId);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM users WHERE id = :userId")
    void deleteUserById(int userId);

    @Query("SELECT COUNT(*) FROM users")
    int getTotalUsers();
}