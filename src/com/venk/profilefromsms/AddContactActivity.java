package com.venk.profilefromsms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.venk.profilefromsms.R;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends Activity {
	private static final int RESULT_PICK_CONTACT = 85500;
	 SQLiteDatabase db;
	 Button btnSave;
	 Button btnCancel;
	 
	 EditText editTextName;
	 EditText editTextNumber;
	 private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =3 ;
	 //EditText editTextCountryCode;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        getActionBar().setTitle("Add new contact"); 
        getActionBar().setIcon(R.drawable.add_contact_48);
        
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        
        btnSave.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View v) {
    			try {    		
	    			editTextName = (EditText)findViewById(R.id.editTextName);
	    			editTextNumber = (EditText)findViewById(R.id.editTextNumber);	    			
	    			ShowMessage message = new ShowMessage();				
	    			
	    			String name = editTextName.getText().toString();
	    			String number = editTextNumber.getText().toString();
	    			//String countryCode = editTextCountryCode.getText().toString();
	    			
	    			boolean isValid = true;
	    			if(name == "" || name.length() == 0) {
	    				message.show(getApplicationContext(), "Invalid name");  
	    				isValid = false;
	    			}
	    			
	    			if(name.length() > 24) {
	    				message.show(getApplicationContext(), "Name is too long");  
	    				isValid = false;
	    			}
	    			
	    			if(number == "" || number.length() == 0) {
	    				message.show(getApplicationContext(), "Invalid number format");  
	    				isValid = false;
	    			}
	    			
	    			if(!isValidMobile(number))
	    			{
	    				message.show(getApplicationContext(), "Invalid number format");  
	    				isValid = false;
	    			}
	    			
	    			DBmethod db = new DBmethod(getApplicationContext());
	    			if(db.IfNumberExists(number))
	    			{
	    				isValid = false;
	    				message.show(getApplicationContext(), "Number already exists.");
	    			}
	    			
	    			
	    			if(isValid)
	    			{						    					    				    				
	    		        db.AddContact(name, number);	    				
	    				editTextName.setText("");			
	        			editTextNumber.setText("");
	    				message.show(getApplicationContext(), "Number successfully saved.");
	    				GoHome();
	    			}			
//	    			else
//	    			{
//	    				message.show(getApplicationContext(), "Please enter valid mobile number.");
//	    			}   
	    				    			
    			}
    			catch (Exception e) {
    				//ShowMessage message = new ShowMessage();				
    				//message.show(getApplicationContext(), "Internall error.");
    			    Log.e("AddContactActivity", "exception", e);
    			}
    		}
    	});
        
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {        	   			
  			Intent intent = new Intent(AddContactActivity.this,MainActivity.class);
  			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
  			startActivity(intent);
            }
         });
        
        int curVersion = Build.VERSION.SDK_INT;
        if(curVersion > Build.VERSION_CODES.LOLLIPOP_MR1)
        {
	        if (ContextCompat.checkSelfPermission(this,
	                Manifest.permission.READ_CONTACTS)
	                != PackageManager.PERMISSION_GRANTED) {                       	  
	                   	ActivityCompat.requestPermissions(this,
	                               new String[]{Manifest.permission.READ_CONTACTS},
	                               MY_PERMISSIONS_REQUEST_READ_CONTACTS);	       
	        }
	        else {
	        	String error = "";
	        }
        }	
	}
    
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
        
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
        }
    }	
	
	public void pickContact(View v)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
            case RESULT_PICK_CONTACT:
                contactPicked(data);
                break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }
    
    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     * @param data
     */
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
			editTextName = (EditText)findViewById(R.id.editTextName);
			editTextNumber = (EditText)findViewById(R.id.editTextNumber);
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            
            Pattern pt = Pattern.compile("[^0-9]");
            Matcher match= pt.matcher(phoneNo);
            while(match.find())
            {
                String s= match.group();
                phoneNo=phoneNo.replaceAll("\\"+s, "");
            }
            
            //phoneNo = phoneNo.replaceAll("\\s+","").replace("+","");            
            name = cursor.getString(nameIndex);
            // Set the value to the textviews
            editTextName.setText(name);
            editTextNumber.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public boolean isValidMobile(String phone) {
		 boolean check=false;
		    if(!Pattern.matches("[a-zA-Z]+", phone)) {
		        if(phone.length() < 7 || phone.length() > 13) {
		            check = false;		            
		        } else {
		            check = true;
		        }
		    } else {
		        check=false;
		    }
		    return check;    
	}
	
	public void GoHome() {
		Intent intent = new Intent(AddContactActivity.this,MainActivity.class);
 		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
 		startActivity(intent);
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
          	 Intent intentKey = new Intent(AddContactActivity.this,KeyActivity.class);
       		startActivity(intentKey);
       		return true;     
         case R.id.menuAbout:  
        	 Intent intentabt = new Intent(AddContactActivity.this,AboutActivity.class);
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
    
    @Override
    public void onBackPressed() {
    	GoHome();
    } 		
}
