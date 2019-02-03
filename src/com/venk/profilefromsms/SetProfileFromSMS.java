
package com.venk.profilefromsms;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SetProfileFromSMS extends BroadcastReceiver{
	final SmsManager sms = SmsManager.getDefault();
    Context context;
    @SuppressWarnings("deprecation")
	public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {
             
            if (bundle != null) {
            	ApplyChanges changes = new ApplyChanges();
            	DBmethod db = new DBmethod(context);            	
            	ArrayList<String> Numberlist = db.GetNumberList();//Get number list from configured list
            	
            	SharedPreferences sharedPrefs = PreferenceManager
        				.getDefaultSharedPreferences(context.getApplicationContext());
            	boolean switchVal = sharedPrefs.getBoolean("prefStatus", false);
            	SmsMessage smsMessage;
            	//String message = "";
            	if(switchVal)	{             	
            		
            		if (Build.VERSION.SDK_INT >= 19) { //KITKAT         
            		    SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);              
            		    smsMessage = msgs[0];            
            		} else {             
            		    Object pdus[] = (Object[]) bundle.get("pdus");             
            		    smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);          
            		}
	                //final Object[] pdusObj = (Object[]) bundle.get("pdus");	                
	                 
	                //for (int i = 0; i < pdusObj.length; i++) {
	                     
	                    //
						//SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
	                    String phoneNumber = smsMessage.getDisplayOriginatingAddress();
	                     
	                    String message = smsMessage.getDisplayMessageBody();
                    	String[] prefix = message.split("@");
						if(prefix.length==3) {
							String key = db.GetKey();
							if(key.equals(prefix[2])) {
								//Applychanges("You", "", prefix[1]);
								changes.Apply(prefix[1], "", context);	
	                    		
	                    		Intent intentNotify = new Intent(context,NotificationActivity.class);
	                    		intentNotify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                    		Bundle bundleNotify = new Bundle();
	                    		bundleNotify.putString("profile", prefix[1].toUpperCase());
	                    		bundleNotify.putString("deliveryNumber", "You");
	                            intentNotify.putExtras(bundleNotify);
	                            context.startActivity(intentNotify); 
							}
						}
						else {
		                    for (String savedNumber: Numberlist) {	                    	
			                    if(phoneNumber.contains(savedNumber))//if number is added into configured list.
			                    {			                    	
			                    	if(prefix[0].equals("PFS") && (prefix[1].toLowerCase().equals("loud") || prefix[1].toLowerCase().equals("silent"))) {
			                    		String senderName = db.GetContactName(savedNumber);
			                    		//Applychanges(senderName, savedNumber, prefix[1]);
			                    		
			                        	changes.Apply(prefix[1], savedNumber, context);	
			                    		
			                    		Intent intentNotify = new Intent(context,NotificationActivity.class);
			                    		intentNotify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			                    		Bundle bundleNotify = new Bundle();
			                    		bundleNotify.putString("profile", prefix[1].toUpperCase());
			                    		bundleNotify.putString("deliveryNumber", senderName);
			                            intentNotify.putExtras(bundleNotify);
			                            context.startActivity(intentNotify); 
			            	    		break;
			                    	}	                    	
			                    }//If
		                    }//Foreach
						}
	 
	                //} // end for loop
				}
              } // bundle is null
 
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
             
        }
    }        	
    /*
    public void Applychanges(String senderName, String savedNumber,String profileMode) {
    	ApplyChanges changes = new ApplyChanges();
    	changes.Apply(profileMode, savedNumber, context);	
		
		Intent intentNotify = new Intent(context,NotificationActivity.class);
		intentNotify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle bundleNotify = new Bundle();
		bundleNotify.putString("profile", profileMode.toUpperCase());
		bundleNotify.putString("deliveryNumber", senderName);
        intentNotify.putExtras(bundleNotify);
        context.startActivity(intentNotify);            	
    }
    */
}
