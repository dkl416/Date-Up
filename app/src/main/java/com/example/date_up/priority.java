package com.example.date_up;

import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class priority extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_priority);
        //final TextView tv = (TextView) findViewById(R.id.textView1);
        RatingBar rb = (RatingBar) findViewById(R.id.priority);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //tv.setText("rating : " + rating);
            }
        });
    }
}
