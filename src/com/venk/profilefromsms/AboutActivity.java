package com.venk.profilefromsms;

import com.venk.profilefromsms.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends Activity {
	 
	 TextView lblFbAddress;
	 TextView lblBlogAddress;
	 TextView lblSupportAddress;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getActionBar().setTitle("About"); 
        getActionBar().setIcon(R.drawable.about_48);
        
        lblFbAddress = (TextView) findViewById(R.id.lblFbAddress);
        lblBlogAddress = (TextView) findViewById(R.id.lblBlogAddress);
        lblSupportAddress = (TextView) findViewById(R.id.lblSupportAddress);
        
        Linkify.addLinks(lblFbAddress, Linkify.ALL);
        Linkify.addLinks(lblBlogAddress, Linkify.ALL);        
        Linkify.addLinks(lblSupportAddress, Linkify.ALL);
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
            	Intent intent = new Intent(AboutActivity.this,MainActivity.class);
         		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         		startActivity(intent);
            return true;     
           case R.id.menuAbout:          	   
              return true;     
            case R.id.menuExit:  
            	finishAffinity();
              return true;           
              default:  
                return super.onOptionsItemSelected(item);  
        }  
    } 
		
}
