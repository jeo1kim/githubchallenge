package com.example.euiwonkim.githubchallenge;

import android.app.Fragment;
import android.content.Intent;
import android.preference.PreferenceManager;
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
        setContentView(R.layout.activity_main);


//        String githubRepoURL = "https://api.github.com/repos/torvalds/linux/pulls";
//        pullRequests = getGithubRequest(githubRepoURL);


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent diffViewIntent = new Intent(MainActivity.this, DiffViewActivity.class);
                Intent prActivity = new Intent(MainActivity.this, PullRequestListActivity.class);

                //pullRequests.get(11).fetchDiff();
                //System.out.println("passing diff"+ pullRequests.get(11).getDiff());
                //diffViewIntent.putExtra("diff", pullRequests.get(11).getDiff());

                MainActivity.this.startActivity(prActivity);

            }
        });



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
