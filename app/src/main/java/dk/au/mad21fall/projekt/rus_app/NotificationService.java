package dk.au.mad21fall.projekt.rus_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NotificationService extends LifecycleService {
    public static final int NOTIFICATION_ID = 100;
    private Repository repo;
    private static boolean running = false;
    private Tutor user;
    private Double tab = 0.0;
    private Notification builder;
    private NotificationManager manager;
    ExecutorService executor;

    public NotificationService(){}

    @Override
    public void onCreate() {super.onCreate();}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        repo = Repository.getRepository(getApplication());

        repo.getPurchases().observe(this, new Observer<ArrayList<Purchases>>() {
            @Override
            public void onChanged(ArrayList<Purchases> _purchases) {
                for (Purchases purchase : _purchases) {
                    tab = tab+purchase.getDrinkPrice();
                }
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Service Channel", "Notification Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, "Service Channel")
                .setContentTitle("Rus App Tab update")
                .setContentText("The current tab will show here" + tab)
                .setTicker("Rus App Tab update")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        startForeground(NOTIFICATION_ID, notification);

        backgroundMethod(this);

        return START_STICKY;
    }

    private void backgroundMethod(Context context){
        if(!running){
            running = true;
            notificationShower(context);
        }
    }

    private void notificationShower(Context context){
        if(executor == null){
            executor = Executors.newSingleThreadExecutor();
        }
        executor.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(864); //86 400 000 = 24 hours
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(running){

                    Notification notification = new NotificationCompat.Builder(context, "Service Channel")
                            .setContentTitle("Rus App Tab update")
                            .setContentText("The current tab is: "+tab)
                            .setTicker("Rus App Tab update")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .build();

                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(NOTIFICATION_ID, notification);

                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        running = false;
    }
}
