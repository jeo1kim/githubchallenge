package com.example.euiwonkim.githubchallenge;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by euiwonkim on 11/10/18.
 * Asynctaks to handle Github api GET request for public repos
 *
 * @Param: String: public repo url
 * @Result: JSON
 */

public class GIthubGetRequest extends AsyncTask<String, Void, List<PullRequest>> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected List<PullRequest> doInBackground(String... strings) {

        String repoUrlString = strings[0];
        JSONObject resultJSON = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStream inStream = null;
        InputStreamReader inReader = null;
        String inputLine;
        JSONArray jsonArray = null;
        List<PullRequest> pullrequestsList  = new ArrayList<>();

        try {
            // create url object from our repo url
            url = new URL(repoUrlString);
            // create http connection
            connection = (HttpURLConnection) url.openConnection();

            // set API methods and other connection params,
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            // establish connection to our url
            connection.connect();

            // get input stream as byte
            inStream = connection.getInputStream();

            // get the input stream to string
            inReader = new InputStreamReader(inStream);

            BufferedReader bReader = new BufferedReader(inReader);
            StringBuilder sBuilder = new StringBuilder();

            // read until null
            while((inputLine = bReader.readLine()) != null) {
                sBuilder.append(inputLine);
            }

            // close input and buffered readers
            bReader.close();
            inReader.close();
            System.out.println("RAW OUTPUT" + sBuilder.toString());
            jsonArray = (JSONArray) new JSONTokener(sBuilder.toString()).nextValue();
            //resultJSON = new JSONObject(sBuilder.toString());

            // pull requests list
            pullrequestsList = new ArrayList<>();

            // split the JSON Array to list of pull request objects
            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonPR = jsonArray.getJSONObject(i);
                pullrequestsList.add(new PullRequest(jsonPR));

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close connection
            if(connection != null){
                connection.disconnect();
            }
        }



        return pullrequestsList;
    }


    @Override
    protected void onPostExecute(List<PullRequest> pullrequestsList) {
        super.onPostExecute(pullrequestsList);

    }
}
