package com.example.geoquizetmp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquizetmp.Controller.CheatActivity;
import com.example.geoquizetmp.Model.Question;
import com.example.geoquizetmp.R;
import com.example.geoquizetmp.Repository.QuestionListRepository;

import java.util.Objects;


public class GeoQuizFragment extends Fragment {

    public static final String FINAL_GEO_QUIZ = "finalGeoQuiz";
    public static final String BUNDLE_M_CURRENT_INDEX = "Bundle_mCurrentIndex";
    public static final String BUNDLE_SCORE = "bundle_score";
    public static final String IS_CLICKED = "IsClicked";
    public static final String EXTRA_QUESTION_ANSWER = "com.example.finalGeoQuiz.extraQuestionAnswer";
    public static final int REQUEST_CODE_CHEAT = 0;
    public static final String BUNDLE_IS_CHEATER = "Bundle_IsCheater";

    private TextView mTextViewQuestion;
    private TextView mScore;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mFirstButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private ImageButton mLastButton;

    private int mCorrectQuestion = 0;
    private int mAnsweredQuestions = 0;
    private LinearLayout main;
    private LinearLayout mFinalScores;
    private TextView mFinalScoreShow;
    private Button mButtonReset;
    private Button mButtonCheat;

    private int mCurrentIndex = 0;
    private Question mQuestion;

    private QuestionListRepository mQuestionListRepository = QuestionListRepository.getInstance();
    int questionTextResId;

    private boolean[] mIsClicked = new boolean[20];
    private boolean[] mIsCheater = new boolean[20];


    public GeoQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questionTextResId = getActivity().getIntent().getIntExtra(QuestionListFragment.
                EXTRA_QUESTION_RESID, 0);

        for (int i = 0; i < mQuestionListRepository.getQuestion().size() ; i++) {
            if (mQuestionListRepository.getQuestion().get(i).getQuestionTextResId() == questionTextResId)
                mCurrentIndex = i;
        }


    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_geo_quiz,container,false);
        findView(view);
        listeners();

        if (savedInstanceState!=null){
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_M_CURRENT_INDEX);
            mCorrectQuestion = savedInstanceState.getInt(BUNDLE_SCORE);
            mScore.setText("   امتیاز شما  " + mCorrectQuestion);
            mIsCheater = savedInstanceState.getBooleanArray(BUNDLE_IS_CHEATER);
            mIsClicked = savedInstanceState.getBooleanArray(IS_CLICKED);
        }

        updateQuestion();
        return view;
    }

    private void findView(View view) {
        mTextViewQuestion = view.findViewById(R.id.txt_view_question_text);
        mTrueButton = view.findViewById(R.id.btn_true);
        mFalseButton = view.findViewById(R.id.btn_false);
        mFirstButton = view.findViewById(R.id.im_btn_first);
        mPrevButton = view.findViewById(R.id.im_btn_prev);
        mNextButton = view.findViewById(R.id.im_btn_next);
        mLastButton = view.findViewById(R.id.im_btn_last);
        mScore = view.findViewById(R.id.txt_view_score_text);
        main = view.findViewById(R.id.main);
        mFinalScoreShow = view.findViewById(R.id.txt_final_score);
        mFinalScores = view.findViewById(R.id.score);
        mButtonReset = view.findViewById(R.id.im_btn_reset);
        mButtonCheat = view.findViewById(R.id.btn_cheat);


    }

    private void listeners() {
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsClicked[mCurrentIndex] = true;
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                checkAnswer(true);
                IsGameOver();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsClicked[mCurrentIndex] = true;
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                checkAnswer(false);
                IsGameOver();
            }
        });

        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int questionTextResId = mQuestionListRepository.getQuestion().get(
                        mCurrentIndex).getQuestionTextResId();
                mTextViewQuestion.setText(questionTextResId);
                if (mIsClicked[mCurrentIndex]) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int questionTextResId = mQuestionListRepository.getQuestion().get(0).
                        getQuestionTextResId();
                mTextViewQuestion.setText(questionTextResId);
                if (mIsClicked[mCurrentIndex]) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionListRepository.getQuestion().size();
                updateQuestion();
                if (mIsClicked[mCurrentIndex]) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCurrentIndex = (mCurrentIndex - 1 + mQuestionListRepository.getQuestion().size())
                        % mQuestionListRepository.getQuestion().size();
                updateQuestion();
                if (mIsClicked[mCurrentIndex]) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheatActivity.class);
                intent.putExtra(EXTRA_QUESTION_ANSWER, mQuestionListRepository.getQuestion().
                        get(mCurrentIndex).isAnswerTrue());
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }

        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null )
            return;

        if (requestCode == REQUEST_CODE_CHEAT )
            mIsCheater[mCurrentIndex] = data.getBooleanExtra(CheatFragment.EXTRA_IS_CHEAT,
                    false);

    }

    private void IsGameOver() {
        if (mAnsweredQuestions == mQuestionListRepository.getQuestion().size()) {
            main.setVisibility(View.GONE);
            mFinalScoreShow.setText("   امتیاز نهایی شما  " + mCorrectQuestion);
            mFinalScores.setVisibility(View.VISIBLE);
        }
    }

    private void updateQuestion() {
        questionTextResId = mQuestionListRepository.getQuestion().get(mCurrentIndex).
                getQuestionTextResId();
        mTextViewQuestion.setText(questionTextResId);
        mScore.setText("   امتیاز شما  " + mCorrectQuestion);
        mFinalScoreShow.setText("   امتیاز شما  " + mCorrectQuestion);
        if (mIsClicked[mCurrentIndex]) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
        IsGameOver();
    }

    private void checkAnswer(boolean userPressed) {
        if (mIsCheater[mCurrentIndex]){
            Toast.makeText(getContext(), R.string.CheatToast, Toast.LENGTH_LONG)
                    .show();
        }
        else {
            if (Objects.equals(mQuestionListRepository.getQuestion().get(mCurrentIndex).isAnswerTrue(), userPressed)) {
                Toast.makeText(getContext(), R.string.toast_correct, Toast.LENGTH_LONG)
                        .show();
                mCorrectQuestion++;
                mAnsweredQuestions++;
                mScore.setText("   امتیاز شما  " + mCorrectQuestion);
                mFinalScoreShow.setText("   امتیاز شما  " + mCorrectQuestion);

            } else {
                Toast.makeText(getContext(), R.string.toast_incorrect, Toast.LENGTH_SHORT)
                        .show();
                mAnsweredQuestions++;
                mScore.setText("   امتیاز شما  " + mCorrectQuestion);
                mFinalScoreShow.setText("   امتیاز شما  " + mCorrectQuestion);

            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(FINAL_GEO_QUIZ, "onSaveInstant: " + mCurrentIndex);
        outState.putInt(BUNDLE_M_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(BUNDLE_SCORE, mCorrectQuestion);
        outState.putBooleanArray(BUNDLE_IS_CHEATER,mIsCheater);
        outState.putBooleanArray(IS_CLICKED, mIsClicked);
    }
}