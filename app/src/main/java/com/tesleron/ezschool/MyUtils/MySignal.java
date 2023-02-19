package com.tesleron.ezschool.MyUtils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.tesleron.ezschool.MainActivity;
import com.tesleron.ezschool.R;

public class MySignal {
    private static MySignal mySignal = null;
    private Context context;
    private NotificationManager manager;
    private final String CHANNEL_ID = "EZSchool Notification";

    private MySignal(Context context, NotificationManager manager){
        this.context = context;
        this.manager = manager;

    }

    public static void init(Context context, NotificationManager systemService){
        if (mySignal == null) {
            mySignal = new MySignal(context, systemService);
        }
    }
    public static MySignal getInstance(){return mySignal;}

    public void toast(String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 400 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(400);
        }

    }

//    public void createNotification(String textContent)
//    {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ezschool_icon)
//                .setContentTitle("EZSchool notification")
//                .setContentText(textContent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//    }
//
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = manager;
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

}
