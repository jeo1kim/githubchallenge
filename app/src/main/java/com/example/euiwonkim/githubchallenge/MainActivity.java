package com.example.euiwonkim.githubchallenge;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

/**
 * Soundtrack: https://www.youtube.com/watch?v=U5IailIzqdc
 *
 * Procore Github Challenge app
 *
 * Given a repo, show the open pull requests and the aggregated diff in side by side UI
 *
 * This is the Main activity of the github challenge android application.
 *
 */
public class MainActivity extends AppCompatActivity {

    private List<PullRequest> pullRequests;
    private android.support.v4.app.Fragment fragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_list);

        createUI();

    }

    /**
     * create the UI for list card view.
     */
    protected void createUI() {
        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new CardFragment();

            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
