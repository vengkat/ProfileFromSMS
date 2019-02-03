package com.venk.profilefromsms;

import com.venk.profilefromsms.R;
import android.app.Activity;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.content.Context;
import android.os.Bundle;

import android.widget.TextView;

public class NotificationActivity extends Activity {
	 
	 TextView lblFbAddress;
	 TextView lblBlogAddress;
	 TextView lblSupportAddress;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        Bundle bundle = getIntent().getExtras();
        String profile = bundle.getString("profile");
        String deliveryNumber = bundle.getString("deliveryNumber");
        
        NotificationCompat.Builder mBuilder =
       		 new NotificationCompat.Builder(this);

       		//Create the intent that’ll fire when the user taps the notification//
        	String notifyMessage = profile + " Profile has been activated by " + deliveryNumber;
       		mBuilder.setSmallIcon(R.drawable.phone_mail_48);
       		mBuilder.setContentTitle("PFS");       		
       		mBuilder.setContentText(notifyMessage);
       		mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(notifyMessage));
       		NotificationManager mNotificationManager =
       		(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

       		mNotificationManager.notify(001, mBuilder.build());
       		finishAffinity();
    }
	
	//public void sendNotification() {

//		NotificationCompat.Builder mBuilder =
//		 new NotificationCompat.Builder(this);
//
//		//Create the intent that’ll fire when the user taps the notification//
//
//		mBuilder.setSmallIcon(R.drawable.phone_ring_64);
//		mBuilder.setContentTitle("My notification");
//		mBuilder.setContentText("Hello World!");
//
//		NotificationManager mNotificationManager =
//		(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//		mNotificationManager.notify(001, mBuilder.build());
	//}		
}
