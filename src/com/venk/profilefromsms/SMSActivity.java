package com.venk.profilefromsms;	

import com.venk.profilefromsms.R;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

public class SMSActivity extends Activity {
   private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
   TextView txtName;
   TextView txtNumber;
   TextView imgLoud;
   TextView imgSilent;
   TextView imgDelete;
   TextView imgClose;
   
   String phoneNo;
   String userName;
   String message;
   
   final String profile_general = "PFS@loud";
   final String profile_silent = "PFS@silent";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.message_activity);
      getActionBar().setTitle("Make an action"); 
      
      txtName = (TextView) findViewById(R.id.txtName);
      txtNumber = (TextView) findViewById(R.id.txtNumber);
      imgLoud = (TextView) findViewById(R.id.txtimgLoud);
      imgSilent = (TextView) findViewById(R.id.txtimgSilent);
      imgDelete = (TextView) findViewById(R.id.txtimgDelete);
      imgClose = (TextView) findViewById(R.id.txtimgClose);
      
      Bundle bundle = getIntent().getExtras();
      userName = bundle.getString("userName");
      phoneNo = bundle.getString("deliveryNumber");
      
      txtName.setText(userName);
      txtNumber.setText(phoneNo);
      
      
      imgLoud.setOnClickListener(new View.OnClickListener() {
         public void onClick(View view) {        
        	//ShowMessage msg = new ShowMessage();
        	//msg.show(getApplicationContext(), "Profile General clicked..");
            sendSMSMessage(profile_general,phoneNo);
            GoHome();
         }
      });
      
      imgSilent.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {        	
        	  //ShowMessage msg = new ShowMessage();
          	//msg.show(getApplicationContext(), "Profile Silent clicked..");
			sendSMSMessage(profile_silent,phoneNo);
			GoHome();
          }
       });
      
      imgDelete.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {        	 
        	  	ShowMessage msg = new ShowMessage();
          		DBmethod db = new DBmethod(getApplicationContext());
          		db.Delete(phoneNo);          	
          		msg.show(getApplicationContext(), "The Number has been deleted.");
          		GoHome();
          }
       });
      
      imgClose.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {        	 			
        	  GoHome();
          }
       });

      int curVersion = Build.VERSION.SDK_INT;
      if(curVersion > Build.VERSION_CODES.LOLLIPOP_MR1)
      {
	        if (ContextCompat.checkSelfPermission(this,
	                Manifest.permission.SEND_SMS)
	                != PackageManager.PERMISSION_GRANTED) {                       	  
	                   	ActivityCompat.requestPermissions(this,
	                               new String[]{Manifest.permission.SEND_SMS},
	                               MY_PERMISSIONS_REQUEST_SEND_SMS);
	                   	            	             
	             }
	        else {
	        	String error = "";
	        }
      }
   }
	
   protected void sendSMSMessage(String messageText, String deliveryNumber) {	   
	  message = messageText;	  
      phoneNo = deliveryNumber;     
      int curVersion = Build.VERSION.SDK_INT;
      if(curVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
      if (ContextCompat.checkSelfPermission(this,
         Manifest.permission.SEND_SMS)
         != PackageManager.PERMISSION_GRANTED) {            
//    	  Toast.makeText(getApplicationContext(), "Kindly check if the permissin is granted in "
//    	  		+ "Settings -> Apps -> ProfileFromSMS -> Permissions",
//                  Toast.LENGTH_LONG).show();    
            	ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            	            	             
      }else {
      	//msg.show(getApplicationContext(), "Has Permission");            	
      	SendTextMsg();
      }
      }
      else {
        	//msg.show(getApplicationContext(), "Has Permission");            	
        	SendTextMsg();
        }
   }
	   /*
   public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
      switch (requestCode) {
         case MY_PERMISSIONS_REQUEST_SEND_SMS: {
            if (grantResults.length > 0
               && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            	SendTextMsg();
            	GoHome();
            } else {            	
            	Toast.makeText(getApplicationContext(), "Permission Denied. Kindly check if the permissin is granted in "
            	  		+ "Settings -> Apps -> ProfileFromSMS -> Permissions",
                          Toast.LENGTH_LONG).show();    
               return;
            }
         }
      }
   }
   */
   
   public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
       switch (requestCode) {
       case MY_PERMISSIONS_REQUEST_SEND_SMS: {
           if (grantResults.length > 0
              && grantResults[0] == PackageManager.PERMISSION_GRANTED) {              	
           } else {            	
              Toast.makeText(getApplicationContext(), 
                 "Permission denied to send SMS. Kindly grant permission in \\\"\\n\" + \n" + 
                 "                     \"            	  		+ \\\"Settings -> Apps -> ProfileFromSMS -> Permissions", Toast.LENGTH_LONG).show();                   
              return;
           }
        }    
       }
   }	
   
   private void SendTextMsg() {
       SmsManager smsManager = SmsManager.getDefault();
       smsManager.sendTextMessage("+" + phoneNo, null, message, null, null);

       Toast.makeText(getApplicationContext(), "Request sent.",
               Toast.LENGTH_LONG).show();    
   }
   
   @Override
   public void onBackPressed() {
	   GoHome();
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_main, menu);
       return true;
   }
   
   @Override  
   public boolean onOptionsItemSelected(MenuItem item) {  
       switch (item.getItemId()) {  
       case R.id.menuHome:                  
    	   GoHome();
         return true;     
       case R.id.menuKey:  
         	 Intent intentKey = new Intent(SMSActivity.this,KeyActivity.class);
      		startActivity(intentKey);
      		return true;     
        case R.id.menuAbout:  
        	Intent intentabt = new Intent(SMSActivity.this,AboutActivity.class);
     		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
     		startActivity(intentabt);   
           return true;     
         case R.id.menuExit:  
        	 finishAffinity();
           return true;           
           default:  
             return super.onOptionsItemSelected(item);  
       }  
   } 
   
	public void GoHome() {
		Intent intent = new Intent(SMSActivity.this,MainActivity.class);
 		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
 		startActivity(intent);
	}
}