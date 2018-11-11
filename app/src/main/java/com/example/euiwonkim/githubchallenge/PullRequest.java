package com.example.euiwonkim.githubchallenge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by euiwonkim on 11/10/18.
 * PullRequest object to store each pull request info
 */

public class PullRequest {



    private String id;
    private String state;
    private String diffUrl;
    private String title;

    private String diff;

    protected PullRequest(JSONObject pullRequest) throws JSONException {

        //System.out.println(pullRequest.toString());
        this.id = pullRequest.getString("id");
        this.state = pullRequest.getString("state");
        this.diffUrl = pullRequest.getString("diff_url");
        this.title = pullRequest.getString("title");

    }

    protected void fetchDiff(){


        DiffGetRequest diffGetRequest = new DiffGetRequest();
        String diff = null;
        try {
            diff = diffGetRequest.execute(this.diffUrl).get();
            //System.out.println("diff response " + diff);
            this.diff = diff;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    protected void setDiff(String diff){
        this.diff = diff;
    }

    protected String getDiff(){
        return this.diff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
