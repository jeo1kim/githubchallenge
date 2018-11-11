package com.example.euiwonkim.githubchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String githubRepoURL = "https://api.github.com/repos/torvalds/linux/pulls";
        List<PullRequest> pullRequests = getGithubRequest(githubRepoURL);

        fetchDiff(pullRequests);


    }

    protected List<PullRequest> getGithubRequest(String githubUrl) {

        GIthubGetRequest githubGetRequest = new GIthubGetRequest();
        JSONObject result = null;

        List<PullRequest> pullRequests = new ArrayList<>();

        try {
            pullRequests = githubGetRequest.execute(githubUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return pullRequests;

    }

    protected void fetchDiff(List<PullRequest> pullRequests){

        for(PullRequest pr : pullRequests){
            pr.fetchDiff();
        }
    }

}
