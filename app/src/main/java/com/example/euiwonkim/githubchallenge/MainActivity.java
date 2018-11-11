package com.example.euiwonkim.githubchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

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

    List<PullRequest> pullRequests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String githubRepoURL = "https://api.github.com/repos/torvalds/linux/pulls";
        pullRequests = getGithubRequest(githubRepoURL);



        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diffViewIntent = new Intent(MainActivity.this, DiffViewActivity.class);

                pullRequests.get(11).fetchDiff();

                System.out.println("passing diff"+ pullRequests.get(11).getDiff());
                diffViewIntent.putExtra("diff", pullRequests.get(11).getDiff());
                MainActivity.this.startActivity(diffViewIntent);

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
        //fetchDiff(pullRequests);
    }

    /**
     * Uses Asynctaks in GithubGetRequest class
     * @param public github repo url
     * @return List of pull request on the github repo
     */
    protected List<PullRequest> getGithubRequest(String githubUrl) {

        // Asynctask
        GIthubGetRequest githubGetRequest = new GIthubGetRequest();
        JSONObject result = null;
        // pull requests list
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

    // TODO: Optimize the diff fetch.
    protected void fetchDiff(List<PullRequest> pullRequests){

        for(PullRequest pr : pullRequests){
            pr.fetchDiff();
        }
    }

}
