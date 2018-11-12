package com.example.euiwonkim.githubchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by euiwonkim on 11/11/18.
 */

public class PullRequestListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_list);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new CardFragment();
            ;
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();


        }

    }
}
