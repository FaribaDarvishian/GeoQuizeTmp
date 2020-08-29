package com.example.geoquizetmp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.geoquizetmp.Fragment.CheatFragment;
import com.example.geoquizetmp.R;

public class CheatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.cheatContainer);

        if (fragment == null) {
            CheatFragment cheatActivityFragment = new CheatFragment();
            fragmentManager.beginTransaction().add(R.id.cheatContainer, cheatActivityFragment).
                    commit();
        }
    }
}