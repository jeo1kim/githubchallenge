package com.example.euiwonkim.githubchallenge;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by euiwonkim on 11/10/18.
 *
 * AsyncTask to get the Diff file from each pull request
 */

public class DiffGetRequest extends AsyncTask<String, Void, String> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected String doInBackground(String... strings) {

        String repoUrlString = strings[0];
        URL url = null;
        HttpURLConnection connection = null;
        InputStream inStream = null;
        InputStreamReader inReader = null;
        String inputLine = null;
        String diffResult = null;

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
            inReader = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            BufferedReader bReader = new BufferedReader(inReader);
            StringBuilder sBuilder = new StringBuilder();

            // read bytes to read newline and tabs
            char[] buff = new char[500];
            for (int charsRead; (charsRead = inReader.read(buff)) != -1; ) {
                sBuilder.append(buff, 0, charsRead);
            }

            // close input and buffered readers
            bReader.close();
            inReader.close();

            // return as string
            diffResult = sBuilder.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Sorry, this diff is unavailable";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close connection
            if(connection != null){
                connection.disconnect();
            }
        }
        return diffResult;
    }


    @Override
    protected void onPostExecute(String diffString) {
        super.onPostExecute(diffString);

    }
}
