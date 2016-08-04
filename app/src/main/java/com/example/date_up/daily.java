package com.example.date_up;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class daily extends MainActivity {

    //public boolean[] dayChecked = {false, false, false, false, false, false, false};
    public boolean IsEvery = false;
    public static int MON = 0;
    public static int TUE = 1;
    public static int WED = 2;
    public static int THU = 3;
    public static int FRI = 4;
    public static int SAT = 5;
    public static int SUN = 6;
    public int day;
    public int period = 0;
    private static String delivermsg2 = "";


    public void displayPeriod() {
        Button start = (Button) findViewById(R.id.start_date);
        Button end = (Button) findViewById(R.id.end_date);
        Calendar calendar = Calendar.getInstance();
        //whatToDo.startDate = calendar;
        start.setText(calendar.get(Calendar.YEAR) + "년 " + (calendar.get(Calendar.MONTH) + 1) + "월 " + calendar.get(Calendar.DAY_OF_MONTH) + "일");
        calendar.add(Calendar.DATE, period);
        //whatToDo.endDate = calendar;
        end.setText(calendar.get(Calendar.YEAR) + "년 " + (calendar.get(Calendar.MONTH) + 1) + "월 " + calendar.get(Calendar.DAY_OF_MONTH) + "일");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_task);
        ImageButton cal = (ImageButton) findViewById(R.id.cal);
        displayPeriod();
        cal.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       Intent intent = new Intent(daily.this, calendar.class);
                                       startActivity(intent);
                                       finish();
                                   }
                               }
        );
        RatingBar rb = (RatingBar) findViewById(R.id.priority);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //tv.setText("rating : " + rating);
            }
        });

    }


    public void addPeriod(View view) {
        EditText edit = (EditText) findViewById(R.id.repeat);
        switch (view.getId()) {
            case R.id.week:
                period += 7;
                break;
            case R.id.month:
                period += 30;
                break;
            case R.id.year:
                period += 365;
                break;
        }
        edit.setText(period + "");
        displayPeriod();
    }

    public void getPeriod(View view) {
        EditText edit = (EditText) findViewById(R.id.repeat);
        String str = edit.getText().toString();
        //앞에 0오는거 예외처리
        period = Integer.parseInt(str);
        edit.setText(period + "");
        edit.setSelection(str.length());
        displayPeriod();
    }

    public void resetPeriod() {
        EditText edit = (EditText) findViewById(R.id.repeat);
        period = 0;
        edit.setText("" + period);
        displayPeriod();
    }


    public void checkEveryday(View view) {
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.sevendays);
        IsEvery ^= true;
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            ((CheckBox) mLinearLayout.getChildAt(i)).setChecked(IsEvery);
        }
    }

    public void okBtn(View v) {
        delivermsg2 = "";
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.sevendays);
        //IsEvery ^= true;
        //IsEvery = false;
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            if (((CheckBox) mLinearLayout.getChildAt(i)).isChecked()) {
                if (i == MON) {
                    delivermsg2 += "Mon ";
                }
                if (i == TUE) {
                    delivermsg2 += "Tue ";
                }
                if (i == WED) {
                    delivermsg2 += "Wed ";
                }
                if (i == THU) {
                    delivermsg2 += "Thu ";
                }
                if (i == FRI) {
                    delivermsg2 += "Fri ";
                }
                if (i == SAT) {
                    delivermsg2 += "Sat ";
                }
                if (i == SUN) {
                    delivermsg2 += "Sun ";
                }
            }

        }

        delivermsg2 += "period: " + period;

        writeToFile(delivermsg2);
        //IsEvery = false;
        Intent intent2 = new Intent(daily.this, MainActivity.class);

        startActivity(intent2);

    }

    public void writeToFile(String data) {
        try {

            FileOutputStream fout = openFileOutput("todo.txt", MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fout);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String deliver() {
        delivermsg2 = MainActivity.deliverMain();
        return delivermsg2;
    }
}
