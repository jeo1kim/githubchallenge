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
    private String imageUrl;
    private String userId;
    private String title;

    private String diff;


    /**
     * Constructor
     * @param pullRequest: JSON result from api call
     * @throws JSONException
     */
    protected PullRequest(JSONObject pullRequest) throws JSONException {

        //System.out.println(pullRequest.toString());
        this.id = pullRequest.getString("number");
        this.state = pullRequest.getString("state");
        this.diffUrl = pullRequest.getString("diff_url");
        this.imageUrl = pullRequest.getJSONObject("user").getString("avatar_url");
        this.userId =  pullRequest.getJSONObject("user").getString("login");
        this.title = pullRequest.getString("title");

    }

    /**
     * fetch the diff file from diff url
     *
     * uses asyntask from diffGetRequest class.
     */
    protected void fetchDiff(){
        // we already fetche the api call for this pull request
        if(this.diff != null){
            return;
        }
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

    /**
     * getters and setters
     */
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
