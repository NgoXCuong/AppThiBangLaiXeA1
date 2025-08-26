package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

// BookmarkedQuestion Entity - Câu hỏi đã bookmark
@Entity(tableName = "bookmarked_questions",
        primaryKeys = {"user_id", "question_id"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Questions.class,
                        parentColumns = "question_id",
                        childColumns = "question_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class BookmarkedQuestion {
    @ColumnInfo(name = "user_id", index = true)
    public int userId;

    @ColumnInfo(name = "question_id", index = true)
    public int questionId;

    @ColumnInfo(name = "bookmarked_at")
    public long bookmarkedAt;

    @ColumnInfo(name = "note")
    public String note; // Ghi chú của người dùng

    public BookmarkedQuestion() {}

    public BookmarkedQuestion(int userId, int questionId, String note) {
        this.userId = userId;
        this.questionId = questionId;
        this.note = note;
        this.bookmarkedAt = System.currentTimeMillis();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public long getBookmarkedAt() {
        return bookmarkedAt;
    }

    public void setBookmarkedAt(long bookmarkedAt) {
        this.bookmarkedAt = bookmarkedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
