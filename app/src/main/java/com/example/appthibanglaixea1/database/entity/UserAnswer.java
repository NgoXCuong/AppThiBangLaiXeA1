package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "user_answers",
        primaryKeys = {"result_id", "question_id"},
        foreignKeys = {
                @ForeignKey(entity = UserResult.class,
                        parentColumns = "result_id",
                        childColumns = "result_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Questions.class,
                        parentColumns = "question_id",
                        childColumns = "question_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Answers.class,
                        parentColumns = "answer_id",
                        childColumns = "answer_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class UserAnswer {
    @ColumnInfo(name = "result_id", index = true)
    public int resultId;

    @ColumnInfo(name = "question_id", index = true)
    public int questionId;

    @ColumnInfo(name = "answer_id", index = true)
    public int answerId;

    @ColumnInfo(name = "is_correct")
    public boolean isCorrect;

    @ColumnInfo(name = "answered_at")
    public long answeredAt;

    public UserAnswer() {}

    public UserAnswer(int resultId, int questionId, int answerId, boolean isCorrect) {
        this.resultId = resultId;
        this.questionId = questionId;
        this.answerId = answerId;
        this.isCorrect = isCorrect;
        this.answeredAt = System.currentTimeMillis();
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public long getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(long answeredAt) {
        this.answeredAt = answeredAt;
    }
}
