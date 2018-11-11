package com.example.euiwonkim.githubchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;



/**
 * Created by euiwonkim on 11/10/18.
 */

public class DiffViewActivity extends Activity {

    private String diffString;

    TextView diffTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);


        diffTextView = (TextView) findViewById(R.id.diffTextView);

        Intent intent = getIntent();
        diffString = intent.getStringExtra("diff");

    }

    @Override
    protected void onStart() {
        super.onStart();


        diffTextView.setText(diffString);


    }

}
