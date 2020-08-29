package com.example.geoquizetmp.Repository;

import com.example.geoquizetmp.Model.Question;
import com.example.geoquizetmp.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionListRepository {
    private static final int QUESTION_SIZE = 20;
    private static QuestionListRepository sInstance;

    public static QuestionListRepository getInstance() {
        if (sInstance == null)
            sInstance = new QuestionListRepository();

        return sInstance;
    }

    private List<Question> mQuestionBank;

    private QuestionListRepository() {
        mQuestionBank = new ArrayList<>();

        Question[] questions = {new Question(R.string.question_australia, false),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),
                new Question(R.string.question_africa, true),
                new Question(R.string.question_americas, false),
                new Question(R.string.question_asia, false),
                new Question(R.string.question_australia, false),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),
                new Question(R.string.question_africa, true),
                new Question(R.string.question_americas, false),
                new Question(R.string.question_asia, false),
                new Question(R.string.question_australia, false),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),
                new Question(R.string.question_africa, true),
                new Question(R.string.question_americas, false),
                new Question(R.string.question_asia, false),
                new Question(R.string.question_americas, false),
                new Question(R.string.question_asia, false)};

        for (int i = 0; i < QUESTION_SIZE; i++) {
            mQuestionBank.add(questions[i]);
        }

    }

    public List<Question> getQuestion() {
        return mQuestionBank;
    }

    public void setQuestion(List<Question> questions) {
        mQuestionBank = questions;
    }

    public Question getQuestions(int id) {
        for (Question question: mQuestionBank) {
            if (question.getQuestionTextResId() == id)
                return question;
        }

        return null;
    }

    public void insertQuestion(Question question) {
        mQuestionBank.add(question);
    }


}
