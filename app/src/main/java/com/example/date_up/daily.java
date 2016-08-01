package com.example.date_up;

import android.content.Intent;
import android.media.Image;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Calendar;

public class daily extends AppCompatActivity {

    //public boolean[] dayChecked = {false, false, false, false, false, false, false};
    //public boolean IsEvery = false;
    public static int MON = 0;
    public static int TUE = 1;
    public static int WED = 2;
    public static int THU = 3;
    public static int FRI = 4;
    public static int SAT = 5;
    public static int SUN = 6;
    public int day;
    public int period = 0;


    public void displayPeriod() {
        Button start = (Button) findViewById(R.id.start_date);
        Button end = (Button) findViewById(R.id.end_date);
        Calendar calendar = Calendar.getInstance();
        start.setText(calendar.get(Calendar.YEAR) + "년 " + (calendar.get(Calendar.MONTH) + 1) + "월 " + calendar.get(Calendar.DAY_OF_MONTH) + "일");
        calendar.add(Calendar.DATE, period);
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
    }

    public String reduceZero(String str) {

        String temp = "";
        int zero;
        int i;
        for (zero = 0; ; zero++)
            if (str.charAt(zero) != '0') break;
        for (i = zero; i < str.length(); i++) {
            temp += str.charAt(i);
        }
        temp+='\0';
        return temp;
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
        period = Integer.parseInt(reduceZero(str));
        edit.setText(period + "");
        edit.setSelection(str.length());
        displayPeriod();
    }

    public void resetPeriod() {
        EditText edit = (EditText) findViewById(R.id.repeat);
        period = 0;
        edit.setText(period + "");
        displayPeriod();
    }

    public void setStart() {

        //달력 띄우
    }

    public void setEnd() {
    }

    public void checkDay(View view) {
        switch (view.getId()) {
            case R.id.mon:

                break;
            case R.id.tue:

                break;
            case R.id.wed:

                break;
            case R.id.thu:

                break;
            case R.id.fri:

                break;
            case R.id.sat:

                break;
            case R.id.sun:

                break;
        }
    }

}
