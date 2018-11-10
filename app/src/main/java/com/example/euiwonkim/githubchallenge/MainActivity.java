package com.example.euiwonkim.githubchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

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
        GIthubGetRequest githubGetRequest = new GIthubGetRequest();
        JSONObject result = null;

        try {
            result = githubGetRequest.execute(githubRepoURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("github api get request" + result);
    }

}
