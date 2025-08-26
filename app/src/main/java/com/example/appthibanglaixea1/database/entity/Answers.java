package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "answers",
        foreignKeys = @ForeignKey(entity = Questions.class,
                parentColumns = "question_id",
                childColumns = "question_id",
                onDelete = ForeignKey.CASCADE))
public class Answers {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "answer_id")
    public int answerId;

    @ColumnInfo(name = "question_id", index = true)
    public int questionId;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "is_correct")
    public boolean isCorrect;

    public Answers() {}

    public Answers(int questionId, String content, boolean isCorrect) {
        this.questionId = questionId;
        this.content = content;
        this.isCorrect = isCorrect;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}