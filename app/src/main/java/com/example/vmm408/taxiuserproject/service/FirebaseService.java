package com.example.vmm408.taxiuserproject.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.eventBusModel.AppIsForegroundEventBusModel;
import com.example.vmm408.taxiuserproject.fragments.DatabaseValueEventListener;
import com.example.vmm408.taxiuserproject.receiver.NotificationClickReceiver;
import com.example.vmm408.taxiuserproject.utils.UserSharedUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.CURRENT_ORDER_REF_KEY;
import static com.example.vmm408.taxiuserproject.models.OrderModel.CurrentOrder;

public class FirebaseService extends Service {
    private FirebaseDatabase database;
    private boolean appInForeground = true;
    private DatabaseValueEventListener currentOrderValue = new DatabaseValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() == null) {
                stopSelf();
                return;
            }
            if (dataSnapshot.getValue().equals("notAccepted")) {
                return;
            }
            CurrentOrder.getOrderModel().setOrderAcceptedUser(dataSnapshot.getValue().toString());
            if (!appInForeground) {
                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                        .notify(111, createNotification());
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        database = FirebaseDatabase.getInstance();
        initValueListener();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initValueListener() {
        DatabaseReference reference = database
                .getReference(CURRENT_ORDER_REF_KEY)
                .child(UserSharedUtils.userSignedInApp(getApplicationContext()))
                .child("orderAcceptedUser");
        reference.addValueEventListener(currentOrderValue);
    }

    private Notification createNotification() {
        Intent intent = new Intent(this, NotificationClickReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 112, intent, Intent.FILL_IN_ACTION);
        return new NotificationCompat.Builder(this)
                .setContentTitle("Taxi Perfect")
                .setContentText("Your order was accepted")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .addAction(new NotificationCompat.Action(0, "OK", pendingIntent))
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .build();
    }

    @Subscribe
    public void getEvent(AppIsForegroundEventBusModel eventBusModel) {
        appInForeground = eventBusModel.getAppForeground().getAppForeground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
