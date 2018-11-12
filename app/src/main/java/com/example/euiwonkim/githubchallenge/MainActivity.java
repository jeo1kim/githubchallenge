package com.example.euiwonkim.githubchallenge;

import android.app.Fragment;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_list);


        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

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
