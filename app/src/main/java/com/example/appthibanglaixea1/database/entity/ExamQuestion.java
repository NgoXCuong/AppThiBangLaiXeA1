package com.example.appthibanglaixea1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "exam_questions",
        primaryKeys = {"exam_id", "question_id"},
        foreignKeys = {
                @ForeignKey(entity = Exams.class,
                        parentColumns = "exam_id",
                        childColumns = "exam_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Questions.class,
                        parentColumns = "question_id",
                        childColumns = "question_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class ExamQuestion {
    @ColumnInfo(name = "exam_id", index = true)
    public int examId;

    @ColumnInfo(name = "question_id", index = true)
    public int questionId;

    @ColumnInfo(name = "question_order") // Thứ tự câu hỏi trong đề
    public int questionOrder;

    public ExamQuestion() {}

    public ExamQuestion(int examId, int questionId, int questionOrder) {
        this.examId = examId;
        this.questionId = questionId;
        this.questionOrder = questionOrder;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }
}