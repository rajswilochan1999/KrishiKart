package com.Krishi.krishikart;



import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.Krishi.krishikart.App.FCM_CHANNEL_ID;

public class FCMMessageReceiverService extends FirebaseMessagingService {

    public static final String TAG="MyTag";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG,"onMessagereceived:called");
        Log.d(TAG,"onMessagereceived:Message received from"+remoteMessage.getFrom());

        if(remoteMessage.getNotification()!=null){

            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();
            //   String icon=remoteMessage.getNotification().getIcon();
            // remoteMessage.getNotification().getImageUrl().toString();


            Notification notification=new NotificationCompat.Builder
                    (this,FCM_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_filter_vintage_black)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setColor(Color.BLUE).build();

            NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1002,notification);
        }


        if(remoteMessage.getData().size()>0){

            Log.d(TAG,"onMessagereceived:DataSize is"+remoteMessage.getData().size());

            for(String key:remoteMessage.getData().keySet()){
                Log.d(TAG,"onMessageReceived : Key" +key+" Data: "+remoteMessage.getData().get(key));

            }

            Log.d(TAG,"onMessagereceived:Data"+remoteMessage.getData().toString());
            //          remoteMessage.getData().get(key)

        }
    }






    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG,"onDeleteMessage:called");

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG,"onNewToken:called");

    }
}
