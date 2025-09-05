package com.example.appthibanglaixea1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.appthibanglaixea1.database.converters.Converters;
import com.example.appthibanglaixea1.database.dao.AnswerDao;
import com.example.appthibanglaixea1.database.dao.BookmarkedQuestionDao;
import com.example.appthibanglaixea1.database.dao.ExamDao;
import com.example.appthibanglaixea1.database.dao.ExamQuestionDao;
import com.example.appthibanglaixea1.database.dao.QuestionCategoryDao;
import com.example.appthibanglaixea1.database.dao.QuestionDao;
import com.example.appthibanglaixea1.database.dao.SettingsDao;
import com.example.appthibanglaixea1.database.dao.StudyProgressDao;
import com.example.appthibanglaixea1.database.dao.UserAnswerDao;
import com.example.appthibanglaixea1.database.dao.UserDao;
import com.example.appthibanglaixea1.database.dao.UserResultDao;
import com.example.appthibanglaixea1.database.entity.Answers;
import com.example.appthibanglaixea1.database.entity.BookmarkedQuestion;
import com.example.appthibanglaixea1.database.entity.ExamQuestion;
import com.example.appthibanglaixea1.database.entity.Exams;
import com.example.appthibanglaixea1.database.entity.QuestionCategory;
import com.example.appthibanglaixea1.database.entity.Questions;
import com.example.appthibanglaixea1.database.entity.Settings;
import com.example.appthibanglaixea1.database.entity.StudyProgress;
import com.example.appthibanglaixea1.database.entity.User;
import com.example.appthibanglaixea1.database.entity.UserAnswer;
import com.example.appthibanglaixea1.database.entity.UserResult;

@Database(
        entities = {
                User.class,
                Settings.class,
                Questions.class,
                Answers.class,
                Exams.class,
                ExamQuestion.class,
                QuestionCategory.class,
                BookmarkedQuestion.class,
                StudyProgress.class,
                UserAnswer.class,
                UserResult.class
        },
        version = 2, // Cập nhật version
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    // DAOs
    public abstract UserDao userDao();
    public abstract SettingsDao settingsDao();
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();
    public abstract ExamDao examDao();
    public abstract ExamQuestionDao examQuestionDao();
    public abstract QuestionCategoryDao questionCategoryDao();
    public abstract BookmarkedQuestionDao bookmarkedQuestionDao();
    public abstract StudyProgressDao studyProgressDao();
    public abstract UserAnswerDao userAnswerDao();
    public abstract UserResultDao userResultDao();

    // Lấy DB Instance
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "driving_test_a1_database"
                            )
                            .fallbackToDestructiveMigration() // Xóa DB cũ khi thay đổi schema
                            .allowMainThreadQueries() // ⚠️ Chạy trên main thread (dev/test)
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
