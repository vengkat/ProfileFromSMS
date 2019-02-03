//
//package com.venk.profilefromsms;
//
//import java.util.ArrayList;
//
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.preference.PreferenceManager;
//import android.telephony.SmsManager;
//import android.telephony.SmsMessage;
//import android.util.Log;
//import android.widget.Toast;
//
//public class ReceiverService extends Service {
//
//	public Context context = this;
//    public Handler handler = null;
//    public static Runnable runnable = null;
//    final SmsManager sms = SmsManager.getDefault();
//    
//    private final BroadcastReceiver receiver = new BroadcastReceiver() {
//    	   @Override
//    	   public void onReceive(Context context, Intent intent) {
//    	        // Retrieves a map of extended data from the intent.
//    	        final Bundle bundle = intent.getExtras();
//
//    	        try {
//    	        	Log.d("Service", "Inside OnReceive"); 
//    	            if (bundle != null) {
//    	            	DBmethod db = new DBmethod(context);
//    	            	ArrayList<String> Numberlist = db.GetNumberList();//Get number list from configured list
//    	            	ApplyChanges changes = new ApplyChanges();
//    	            	
//    	            	
//    	            	SharedPreferences sharedPrefs = PreferenceManager
//    	        				.getDefaultSharedPreferences(context.getApplicationContext());
//    	            	boolean switchVal = sharedPrefs.getBoolean("prefStatus", false);
//    	            	
//    	            	if(switchVal)	{             
//    		
//    		                final Object[] pdusObj = (Object[]) bundle.get("pdus");	                
//    		                 
//    		                for (int i = 0; i < pdusObj.length; i++) {
//    		                     
//    		                    @SuppressWarnings("deprecation")
//    							SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//    		                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//    		                     
//    		                    //String senderNum = phoneNumber.startsWith("+") ? phoneNumber.substring(1) : phoneNumber;
//    		                    //String prefRemNo = phoneNumber.substring(phoneNumber.length() - 10);
//    		                    
//    		                    //Toast.makeText(context, "Sender Number - " + phoneNumber,
//    		                      //      Toast.LENGTH_LONG).show();
//    		                    //Toast.makeText(context, "trimmed Number - " + prefRemNo,
//    		                      //      Toast.LENGTH_LONG).show();
//    		                    for (String savedNumber: Numberlist) {
//    			                    if(phoneNumber.contains(savedNumber))//if number is added into configured list.
//    			                    {
//    			                    	String message = currentMessage.getDisplayMessageBody();
//    			                    	String[] prefix = message.split("@");
//    			                    	if(prefix[0].equals("PFS") && (prefix[1].toLowerCase().equals("loud") || prefix[1].toLowerCase().equals("silent"))) {
//    			                    		changes.Apply(prefix[1], savedNumber, context);	
//    			                    		String senderName = db.GetContactName(savedNumber);
//    			                    		Intent intentNotify = new Intent(context,NotificationActivity.class);
//    			                    		intentNotify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    			                    		Bundle bundleNotify = new Bundle();
//    			                    		bundleNotify.putString("profile", prefix[1].toUpperCase());
//    			                    		bundleNotify.putString("deliveryNumber", senderName);
//    			                            intentNotify.putExtras(bundleNotify);
//    			                            context.startActivity(intentNotify);
//    			                            
//    			            	    		break;
//    			                    	}	                    	
//    			                    }//If
//    		                    }//Foreach
//    		 
//    		                } // end for loop
//    					}
//    	              } // bundle is null
//    	 
//    	        } catch (Exception e) {
//    	            Log.e("Service", "Exception smsReceiver" +e);
//    	             
//    	        }
//    	    }
//    	};
//    	
//	@Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//    	Log.d("Service", "Inside onCreate"); 
//        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
//        filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);        
//        registerReceiver(receiver, filter);
//    }
//
//    @Override
//    public void onDestroy() {
//    	Log.d("Service", "Inside onDestroy");
//        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
//        //handler.removeCallbacks(runnable);
//        Toast.makeText(this, "Service stopped.", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onStart(Intent intent, int startid) {
//    	Log.d("Service", "Inside onStart");
//        Toast.makeText(this, "Service started.", Toast.LENGTH_LONG).show();
//    }
//
//}
