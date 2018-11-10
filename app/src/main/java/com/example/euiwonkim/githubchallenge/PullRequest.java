package com.example.euiwonkim.githubchallenge;

import org.json.JSONObject;

/**
 * Created by euiwonkim on 11/10/18.
 * PullRequest object to store each pull request info
 */

public class PullRequest {

    String id;
    String state;
    String diffUrl;
    String title;

    protected PullRequest(JSONObject pullRequest){

        System.out.println(pullRequest.toString());



    }



}
