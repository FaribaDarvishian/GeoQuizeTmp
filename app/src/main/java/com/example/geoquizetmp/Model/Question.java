package com.example.geoquizetmp.Model;

public class Question {
    private int mQuestionTextResId;
    private boolean mIsAnswerTrue;

    public int getQuestionTextResId() {
        return mQuestionTextResId;
    }

    public void setQuestionTextResId(int questionTextResId) {
        mQuestionTextResId = questionTextResId;
    }

    public boolean isAnswerTrue() {
        return mIsAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mIsAnswerTrue = answerTrue;
    }

    public Question(int questionTextResId, boolean isAnswerTrue) {
        mQuestionTextResId = questionTextResId;
        mIsAnswerTrue = isAnswerTrue;
    }

    public Question() {
    }
}
