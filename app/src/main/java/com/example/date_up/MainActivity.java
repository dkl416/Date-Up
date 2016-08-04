package com.example.date_up;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

class task {

    String toDo;
    Calendar startDate;
    Calendar endDate;
    float priority;
    //boolean DS;
    ImageView mImageView;
}

//http://androidhuman.com/193 ui store
public class MainActivity extends AppCompatActivity {


    private ArrayList<String> arrayListToDo;
    private ArrayAdapter<String> arrayAdapterToDo;
    public static int DAILY = 0;
    public static int SPECIAL = 1;
    private static String delivermsg = "";
    public task whatToDo;
    NotificationCompat.Builder dBuilder;
    NotificationCompat.Builder sBuilder;
    NotificationManager mNotificationManager;
    String example = "Suhyun's birthday on 2 Oct";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendNotification();
        expiryDate();
        arrayListToDo = new ArrayList<String>();
        readFile();
        arrayAdapterToDo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListToDo);

        ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
        listViewToDo.setAdapter(arrayAdapterToDo);

        registerForContextMenu(listViewToDo);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            delivermsg = extras.getString("delivermsg2");
//            arrayListToDo.add(delivermsg);
//            delivermsg = "";
//            arrayAdapterToDo.notifyDataSetChanged();
//
//            //The key argument here must match that used in the other activity
//        }
        EditText editText = (EditText) findViewById(R.id.add_to_do);
        editText.setText(delivermsg);
    }


    public void sendNotification() {
        String ns = Context.NOTIFICATION_SERVICE;

        //for dbuilder
        Intent intent = new Intent(this, ButtonReceiver.class);
        intent.putExtra("notificationId", 0);
        PendingIntent pintent = PendingIntent.getBroadcast(this, 0, intent, 0);

        //for sbuilder
        Intent intent1 = new Intent(this, ButtonReceiver1.class);
        intent1.putExtra("notificationId", 1);
        PendingIntent pintent1 = PendingIntent.getBroadcast(this, 1, intent1, 0);

        try {
            InputStream inputStream = this.getResources().openRawResource(R.raw.todo);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String string;
            Log.i("ON CREATE", "Hi the create has occured.");

            if ((string = bufferedReader.readLine()) != null) {

                example = string;

            } else return;

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.d)
                        .setContentTitle("Date-Up")
                        .setContentText(example)
                        .addAction(R.drawable.blank, "Delete", pintent)
                        .setAutoCancel(false)
                        .setOngoing(true);


        sBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.s)
                        .setContentTitle("Date-Up")
                        .setContentText("Suhyun's Birthday on 2 Oct")
                        .addAction(R.drawable.blank, "Delete", pintent1)
                        .setAutoCancel(false)
                        .setOngoing(true);
        // Creates an explicit intent for an Activity in your app


        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        dBuilder.setContentIntent(resultPendingIntent);
        sBuilder.setContentIntent(resultPendingIntent);

        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //dbuilderid = NotificationID.getID();
        mNotificationManager.notify(1, sBuilder.build());
        mNotificationManager.notify(0, dBuilder.build());

    }

    public void updateNoti(View v) {
        example = "updating message";
        dBuilder.setContentText(example);
        mNotificationManager.notify(0, dBuilder.build());
    }

    public void expiryDate() {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("notification_id", 0);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

// set for 30 seconds later
        alarmMgr.set(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis() + 5000, alarmIntent);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() != R.id.listViewToDo) {
            return;
        }

        menu.setHeaderTitle("What would you like to do?");
        String[] options = {"Delete Task", "Return"};

        for (String option : options) {
            menu.add(option);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedIndex = info.position;

        if (item.getTitle().equals("Delete Task")) {
            arrayListToDo.remove(selectedIndex);
            arrayAdapterToDo.notifyDataSetChanged();
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        try {
            Log.i("ON BACK PRESSED", "Hi, the on back pressed event has occured.");

            PrintWriter pw = new PrintWriter(openFileOutput("todo.txt", Context.MODE_PRIVATE));

            for (String toDo : arrayListToDo) {
                pw.println(toDo);
            }
            pw.close();

        } catch (Exception e) {
            Log.i("ON BACK PRESSED", e.getMessage());
        }
    }

    public void deleteButton(View view) {
        EditText editText = (EditText) findViewById(R.id.add_to_do);
        editText.setText("");
    }

    public void selectDS(View view) {

        final String items[] = {"Daily", "Special"};
        AlertDialog.Builder _alert = new AlertDialog.Builder(this);
        _alert.setTitle("Add To Do");
        _alert.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            int choice = 0;

            public void onClick(DialogInterface dialog, int whichButton) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                if (((AlertDialog) dialog).getListView().getCheckedItemPosition() == DAILY) {
                    EditText editTextToDo = (EditText) findViewById(R.id.add_to_do);
                    String toDo = editTextToDo.getText().toString().trim();
                    delivermsg = toDo;

                    Intent intent = new Intent(MainActivity.this, daily.class);
                    startActivity(intent);
//Toast.makeText(getApplicationContext(),"Daily",Toast.LENGTH_LONG).show();
                } else if (((AlertDialog) dialog).getListView().getCheckedItemPosition() == SPECIAL) {

                    Intent intent1 = new Intent(MainActivity.this, special.class);
                    startActivity(intent1);
                }
            }
        }).setNegativeButton("Back", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        _alert.show();
    }

    public void addButton(View v) {
        EditText editTextToDo = (EditText) findViewById(R.id.add_to_do);
        whatToDo.priority = 0;
        whatToDo.toDo = editTextToDo.getText().toString().trim();

        if (whatToDo.toDo.isEmpty()) {
            return;
        }

        arrayAdapterToDo.add(whatToDo.toDo);
        arrayAdapterToDo.notifyDataSetChanged();
        editTextToDo.setText("");
    }

    public static String deliverMain() {
        return delivermsg;
    }

    public void readFile() {

        try {
            InputStream inputStream = this.getResources().openRawResource(R.raw.todo);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String string;
            Log.i("ON CREATE", "Hi the create has occured.");

            while ((string = bufferedReader.readLine()) != null) {

                arrayListToDo.add(string);

            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}