package com.venk.profilefromsms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KeyActivity extends Activity {
	
	Button btnSave;
 	Button btnCancel;
	 
 	EditText editTextKey;	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.key);
        getActionBar().setTitle("Manage Key"); 
        getActionBar().setIcon(R.drawable.add_contact_48);
        
        btnSave = (Button) findViewById(R.id.btnSave_key);
        btnCancel = (Button) findViewById(R.id.btnCancel_key);
        editTextKey = (EditText)findViewById(R.id.editTextKey);
        
        DBmethod db = new DBmethod(getApplicationContext());
        editTextKey.setText(db.GetKey()); 
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {        	   		
            	ShowMessage message = new ShowMessage();
            	String key = editTextKey.getText().toString();
            	if(key.length() == 4) {
	            	DBmethod db = new DBmethod(getApplicationContext());
	            	db.UpdateKey(key);            						
					message.show(getApplicationContext(), "Key updated successfully.");
            	}
            	else {
            		message.show(getApplicationContext(), "Key must be 4 digits long.");
            	}
            }
         });
        
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {        	   			
  			Intent intent = new Intent(KeyActivity.this,MainActivity.class);
  			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
  			startActivity(intent);
            }
         });
	}
}
