package com.example.geoquizetmp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.geoquizetmp.R;


public class CheatFragment extends Fragment {

    public static final String EXTRA_IS_CHEAT = "com.example.finalGeoQuiz.ExtraIsCheat";
    public static final String BUNDLE_M_IS_CHEATED = "com.example.finalGeoQuiz.mIsCheated";
    private Button mButton_cheat;
    private TextView mTextView_ShowTheCorrectAnswer;
    private boolean mIsAnswerTrue;
    private boolean mIsCheated;

    public CheatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIsAnswerTrue = getActivity().getIntent().getBooleanExtra(GeoQuizFragment.
                EXTRA_QUESTION_ANSWER,false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cheat,container,false);

        findView(view);
        setListers();
        if (savedInstanceState != null) {
            mIsCheated = savedInstanceState.getBoolean(BUNDLE_M_IS_CHEATED,false);
        }

        if (mIsCheated) {
            if (mIsAnswerTrue) {
                mTextView_ShowTheCorrectAnswer.setText(R.string.button_true);
            } else {
                mTextView_ShowTheCorrectAnswer.setText(R.string.button_false);
            }
        }
        return view;
    }

    private void setListers() {
        mButton_cheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mIsAnswerTrue) {
                    mTextView_ShowTheCorrectAnswer.setText(R.string.button_true);
                } else {
                    mTextView_ShowTheCorrectAnswer.setText(R.string.button_false);
                }

                mIsCheated = true;
            }
        });

        setShownAnswerResult(true);
    }

    private void findView(View view) {
        mButton_cheat = view.findViewById(R.id.btn_show_cheat);
        mTextView_ShowTheCorrectAnswer = view.findViewById(R.id.textView_Cheat_answer_show);
    }

    private void setShownAnswerResult(boolean isCheat) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT, isCheat);
        getActivity().setResult(getActivity().RESULT_OK, intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_M_IS_CHEATED, mIsCheated);
    }
}