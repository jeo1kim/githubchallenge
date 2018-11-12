package com.example.euiwonkim.githubchallenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;



/**
 * Created by euiwonkim on 11/10/18.
 *
 * This activity shows the diff in 2 side by side textview.
 * Changes removed are highlighted in red and added in green
 */

public class DiffViewActivity extends AppCompatActivity {

    private String diffString;
    private String diffRemoved;
    private String diffAdded;
    private TextView diffLeftTextView;
    private TextView diffRightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);


        diffLeftTextView = findViewById(R.id.diffLeftTextView);
        diffRightTextView = findViewById(R.id.diffRightTextView);

        Intent intent = getIntent();
        diffString = intent.getStringExtra("diff");
        makeDiff(diffString);

        setTitle("View Diff");
    }

    @Override
    protected void onStart() {
        super.onStart();

        diffLeftTextView.setText(Html.fromHtml(diffRemoved), TextView.BufferType.SPANNABLE);
        diffRightTextView.setText(Html.fromHtml(diffAdded), TextView.BufferType.SPANNABLE);

    }

    /**
     * Functoin to separate the removed and added changes in the pull request's diff file.
     *
     * @param diff
     */
    protected void makeDiff(String diff) {

        String[] lines = diff.split("\\r?\\n");

        StringBuilder removed = new StringBuilder();
        StringBuilder added = new StringBuilder();

        // reconstruct the remove and added diff texts
        for(String line : lines) {
            int len = line.length()-1;
            if(line.charAt(0) == '-'){

                // highlight with html
                String str = "<span style='background-color: #F08080'>"+ line +"</span>";
                removed.append(str);
                removed.append("<br>");
                // replace + lines with padding
                String pad = paddChars(line);
                added.append(pad);
            }else if(line.charAt(0) == '+'){

                // do some highlightins
                String str = "<span style='background-color: #98FB98'>"+ line +"</span>";
                added.append(str);
                added.append("<br>");
                // replace - lines with padding
                String pad = paddChars(line);
                removed.append(pad);
            }else{
                // just append other lines normally
                removed.append(line);
                added.append(line);
                removed.append("<br>");
                added.append("<br>");
            }

        }

        diffRemoved = removed.toString();
        diffAdded = added.toString();

    }

    /**
     * this function is used to pad the chars with spaces in the diff window
     * @param s
     * @return newly formated String
     */
    protected String paddChars(String s) {

        String paddingCharacter = "  ";
        StringBuffer padding = new StringBuffer();

        while(padding.length() < s.length() ){
            padding.append(paddingCharacter);
        }
        // new line at the end
        padding.append("<br>");
        if(s.length() >55) {
            padding.append("<br>");
        }
        return padding.toString();
    }

}
