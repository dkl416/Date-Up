package com.example.date_up;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by 도경 on 2016-08-04.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent __intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = __intent.getIntExtra("notification_id", -1);

        if (id >= 0) {
            notificationManager.cancel(id);

            //for dbuilder
            Intent intent = new Intent(context, ButtonReceiver.class);
            intent.putExtra("notificationId", 0);
            PendingIntent pintent = PendingIntent.getBroadcast(context, 0, intent, 0);

            NotificationCompat.Builder dBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.d)
                            .setContentTitle("Date-Up")
                            .setContentText("Meet Dokyung at 11 pm")
                            .addAction(R.drawable.blank, "Delete", pintent)
                            .setColor(context.getResources().getColor(R.color.colorNotification))
                            .setAutoCancel(false)
                            .setOngoing(true);

            Intent notificationIntent = new Intent(context, context.getClass());
            //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            dBuilder.setContentIntent(resultPendingIntent);

            notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            //dbuilderid = NotificationID.getID();
            notificationManager.notify(0, dBuilder.build());
        }
    }
}
