package com.venk.profilefromsms;

import java.util.ArrayList;

import com.venk.profilefromsms.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ContactListActivity extends Activity {
    SQLiteDatabase db;	
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        getActionBar().setTitle("Configured List"); 
        getActionBar().setIcon(R.drawable.contact_list_1_48);
        
        //ListView listView1 = (ListView) findViewById(R.id.list);        
        ArrayList<ContactList> Numbers = new ArrayList<ContactList>();
        
        db=openOrCreateDatabase("MobileAssistant.db", Context.MODE_PRIVATE, null);
        //db.execSQL("DROP TABLE IF EXISTS ContactList");
        db.execSQL("CREATE TABLE IF NOT EXISTS ContactList(SlN0 integer primary key autoincrement NOT NULL, Name VARCHAR  NOT NULL, MobileNumber VARCHAR  NOT NULL);");
        //db.execSQL("CREATE TABLE IF NOT EXISTS ContactList(SlN0 integer primary key autoincrement NOT NULL, Name VARCHAR  NOT NULL);");
		//Cursor cur=db.rawQuery("SELECT * FROM ContactList", null);
        //db=openOrCreateDatabase("MobileAssistant.db", Context.MODE_PRIVATE, null);				
		Cursor c=db.rawQuery("SELECT Name, MobileNumber FROM ContactList", null);		
		c.getCount();
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			ContactList lst = new ContactList();
			lst.setContactName(c.getString(0));
			lst.setContactNumber(c.getString(1));
			Numbers.add(lst);
			c.moveToNext();
		}
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
         //       android.R.layout.simple_list_item_1, Numbers);
		//listView1.setAdapter(adapter);
		
		CustomListAdapter adapter = new CustomListAdapter(Numbers, this);
		
        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.list);
        lView.setAdapter(adapter);
		
		c.close();	
		db.close();
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
	
	public void GoHome() {
		Intent intent = new Intent(ContactListActivity.this,MainActivity.class);
 		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
 		startActivity(intent);
	}
    
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case R.id.menuHome:                  
        	GoHome();         
        	return true;
        case R.id.menuKey:  
          	 Intent intentKey = new Intent(ContactListActivity.this,KeyActivity.class);
       		startActivity(intentKey);
       		return true;     
         case R.id.menuAbout:  
        	 Intent intent = new Intent(ContactListActivity.this,AboutActivity.class);
      		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      		startActivity(intent);   
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
