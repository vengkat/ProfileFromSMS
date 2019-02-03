package com.venk.profilefromsms;

import com.venk.profilefromsms.R;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings;

import android.widget.CompoundButton.OnCheckedChangeListener;


public class MainActivity extends Activity {
	private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
	private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS =1 ;
	private static final int MY_PERMISSIONS_REQUEST_READ_SMS =2 ;
	private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =3 ;
	
	boolean doubleBackToExitPressedOnce = false;
	private TextView switchStatus;
	 private Switch mySwitch;	
	 Button btnAdd;
	 
	 Context context;
	 SQLiteDatabase db;	 	 
	 Button btnContactList ;
	 public Cursor cur;

	 private NotificationManager mNotificationManager;
	 //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	 
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startService(new Intent(this, ReceiverService.class));
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("Profile From SMS"); 
        
        mySwitch = (Switch) findViewById(R.id.switch1);
        switchStatus = (TextView) findViewById(R.id.lblSwitchStatus);
        btnAdd = (Button) findViewById(R.id.btnAdd);                
        btnContactList = (Button)findViewById(R.id.btnViewContacts);
        
        //mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        
        final SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
        

        if(sharedPrefs.contains("prefStatus"))
        {
        	boolean switchValue = sharedPrefs.getBoolean("prefStatus", false);
        	
        	if(switchValue) {
        		switchStatus.setText("Profile Set is currently Enabled"); 
   		     mySwitch.setChecked(true);
        	}
        	else {
        		switchStatus.setText("Profile Set is currently Enabled"); 
   		     	mySwitch.setChecked(false);	
        	}		     
        }
        else
        {
        	Editor editor = sharedPrefs.edit();
        	editor.putBoolean("prefStatus", true);
        	editor.commit();
        	switchStatus.setText("Profile Set is currently Enabled"); 
        	mySwitch.setChecked(true);
        }
        //set the switch to ON 

        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {        
        	 @Override
         public void onCheckedChanged(CompoundButton buttonView,
           boolean isChecked) {
        		 
        		 Editor editor = sharedPrefs.edit();
        		 if(isChecked){        		      
        		      //switchStatus.setText(sharedPrefs.getString("prefSendReport", "NULL"));
        		     editor.putBoolean("prefStatus", true);
        		     editor.commit();
        		     switchStatus.setText("Profile Set is currently Enabled");        		     
        		    }else{	        		    
        		    	editor.putBoolean("prefStatus", false);
	        		   editor.commit();
	        		   switchStatus.setText("Profile Set is currently Disabled");
        		    }
         }
        }); 
        
        btnAdd.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this,AddContactActivity.class);
        		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        		startActivity(intent);
        	}
        });
        
        btnContactList.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(MainActivity.this,ContactListActivity.class);
        		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        		startActivity(intent);
        	}
        });
        
        int curVersion = Build.VERSION.SDK_INT;
        if(curVersion > Build.VERSION_CODES.LOLLIPOP_MR1)
        {
	        if (ContextCompat.checkSelfPermission(this,
	                Manifest.permission.READ_SMS)
	                != PackageManager.PERMISSION_GRANTED) {                       	  
	                   	ActivityCompat.requestPermissions(this,
	                               new String[]{Manifest.permission.READ_SMS},
	                               MY_PERMISSIONS_REQUEST_READ_SMS);
	                   	            	             
	             }
	        /*
	        if (ContextCompat.checkSelfPermission(this,
	                Manifest.permission.SEND_SMS)
	                != PackageManager.PERMISSION_GRANTED) {                       	  
	                   	ActivityCompat.requestPermissions(this,
	                               new String[]{Manifest.permission.SEND_SMS},
	                               MY_PERMISSIONS_REQUEST_SEND_SMS);
	                   	            	             
	             }
	        
	        if (ContextCompat.checkSelfPermission(this,
	                Manifest.permission.RECEIVE_SMS)
	                != PackageManager.PERMISSION_GRANTED) {                       	  
	                   	ActivityCompat.requestPermissions(this,
	                               new String[]{Manifest.permission.RECEIVE_SMS},
	                               MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
	                   	            	             
	             }
	        

	        
	        if (ContextCompat.checkSelfPermission(this,
	                Manifest.permission.READ_CONTACTS)
	                != PackageManager.PERMISSION_GRANTED) {                       	  
	                   	ActivityCompat.requestPermissions(this,
	                               new String[]{Manifest.permission.READ_CONTACTS},
	                               MY_PERMISSIONS_REQUEST_READ_CONTACTS);
	                   	            	             
	             }
	        */
//		        if(!mNotificationManager.isNotificationPolicyAccessGranted()) {
//		        	Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//	                startActivity(intent);
//		        }
	        }
    }
    
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
        
        case MY_PERMISSIONS_REQUEST_READ_SMS: {
            if (grantResults.length > 0
               && grantResults[0] == PackageManager.PERMISSION_GRANTED) {              	
            } else {            	
               Toast.makeText(getApplicationContext(), 
                  "Permission denied to read SMS. Kindly grant permission in \\\"\\n\" + \n" + 
                  "                     \"            	  		+ \\\"Settings -> Apps -> ProfileFromSMS -> Permissions", Toast.LENGTH_LONG).show();                   
               return;
            }
         }
        /*
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
           
           case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
               if (grantResults.length > 0
                  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {              	
               } else {            	
                  Toast.makeText(getApplicationContext(), 
                     "Permission denied to receive SMS. Kindly grant permission in \"\n" + 
                     "            	  		+ \"Settings -> Apps -> ProfileFromSMS -> Permissions", Toast.LENGTH_LONG).show();                   
                  return;
               }
            }
           

           
           case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
               if (grantResults.length > 0
                  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {              	
               } else {            	
                  Toast.makeText(getApplicationContext(), 
                     "Permission denied to read contacts. Kindly grant permission in \\\"\\n\" + \n" + 
                     "                     \"            	  		+ \\\"Settings -> Apps -> ProfileFromSMS -> Permissions", Toast.LENGTH_LONG).show();                   
                  return;
               }
            }
           */
        }
     }


@Override
public void onBackPressed() {
    if (doubleBackToExitPressedOnce) {
        super.onBackPressed();
        return;
    }

    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

    new Handler().postDelayed(new Runnable() {

        @Override
        public void run() {
            doubleBackToExitPressedOnce=false;                       
        }
    }, 2000);
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
          return true;     
        case R.id.menuKey:  
       	 Intent intentKey = new Intent(MainActivity.this,KeyActivity.class);
    		startActivity(intentKey);
    		return true;     
         case R.id.menuAbout:  
        	 Intent intent = new Intent(MainActivity.this,AboutActivity.class);
     		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
     		startActivity(intent);              
          case R.id.menuExit:  
        	  finishAffinity();
          	//finishAndRemoveTask (); 
            return true;           
          default:  
              return super.onOptionsItemSelected(item);   
        }
    }        
}
