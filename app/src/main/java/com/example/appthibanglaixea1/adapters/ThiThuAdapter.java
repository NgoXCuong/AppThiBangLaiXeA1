package com.example.appthibanglaixea1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthibanglaixea1.R;
import com.example.appthibanglaixea1.database.entity.Answers;
import com.example.appthibanglaixea1.database.entity.QuestionWithAnswers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThiThuAdapter extends RecyclerView.Adapter<ThiThuAdapter.ViewHolder> {
    private Context context;
    private List<QuestionWithAnswers> questionList;
    private Map<Integer, Integer> selectedAnswers = new HashMap<>();

    public ThiThuAdapter(Context context, List<QuestionWithAnswers> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionWithAnswers qwa = questionList.get(position);
        holder.questionText.setText((position + 1) + ". " + qwa.getQuestion().getContent());

        holder.radioGroup.removeAllViews();
        for (Answers ans : qwa.getAnswers()) {
            RadioButton rb = new RadioButton(context);
            rb.setText(ans.getContent());
            rb.setOnClickListener(v ->
                    selectedAnswers.put(qwa.getQuestion().getQuestionId(), ans.getId())
            );
            holder.radioGroup.addView(rb);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public Map<Integer, Integer> getSelectedAnswers() {
        return selectedAnswers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;
        RadioGroup radioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.txtQuestion);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }
}
