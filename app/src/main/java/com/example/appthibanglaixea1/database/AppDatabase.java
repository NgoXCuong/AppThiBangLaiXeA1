package com.example.appthibanglaixea1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.appthibanglaixea1.database.converters.Converters;
import com.example.appthibanglaixea1.database.dao.SettingsDao;
import com.example.appthibanglaixea1.database.dao.UserDao;
import com.example.appthibanglaixea1.database.entity.Settings;
import com.example.appthibanglaixea1.database.entity.User;

@Database(
        entities = {User.class, Settings.class}, // ✅ Thêm Settings.class
        version = 2, // ✅ Tăng version lên 2
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract SettingsDao settingsDao(); // ✅ Thêm method này

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "driving_test_a1_database")
                            .fallbackToDestructiveMigration() // ✅ Xóa DB cũ khi update
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}