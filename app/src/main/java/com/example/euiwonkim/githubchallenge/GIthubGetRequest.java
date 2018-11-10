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

/**
 * Created by euiwonkim on 11/10/18.
 */

public class GIthubGetRequest extends AsyncTask<String, Void, JSONObject> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected JSONObject doInBackground(String... strings) {

        String repoUrlString = strings[0];
        JSONObject resultJSON = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStream inStream = null;
        InputStreamReader inReader = null;
        String inputLine;

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
            JSONArray jsonArray = (JSONArray) new JSONTokener(sBuilder.toString()).nextValue();
            //resultJSON = new JSONObject(sBuilder.toString());

            

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close connection
            if(connection != null){
                connection.disconnect();
            }
        }

        return resultJSON;
    }


    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
