package com.example.appthibanglaixea1.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.appthibanglaixea1.database.entity.BookmarkedQuestion;

import java.util.List;
@Dao
public interface BookmarkedQuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookmark(BookmarkedQuestion bookmark);

    @Delete
    void deleteBookmark(BookmarkedQuestion bookmark);

    @Query("SELECT * FROM bookmarked_questions WHERE user_id = :userId")
    LiveData<List<BookmarkedQuestion>> getBookmarksByUser(int userId);

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_questions WHERE user_id = :userId AND question_id = :questionId)")
    boolean isBookmarked(int userId, int questionId);
}
