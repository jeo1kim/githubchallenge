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

    TextView diffLeftTextView;
    TextView diffRightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);


        diffLeftTextView = findViewById(R.id.diffLeftTextView);
        diffRightTextView = findViewById(R.id.diffRightTextView);

        Intent intent = getIntent();
        diffString = intent.getStringExtra("diff");

    }

    @Override
    protected void onStart() {
        super.onStart();


        diffLeftTextView.setText(diffString);
        diffRightTextView.setText(diffString);


    }

}
