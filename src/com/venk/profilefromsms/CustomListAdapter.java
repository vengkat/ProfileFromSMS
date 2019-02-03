
package com.venk.profilefromsms;
import java.util.ArrayList;

import com.venk.profilefromsms.R;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter implements ListAdapter { 
	
	private ArrayList<ContactList> list = new ArrayList<ContactList>(); 
	private Context context; 
	SQLiteDatabase database;
	public String userName = "";
	public String deliveryNumber = "";
	public CustomListAdapter(ArrayList<ContactList> list, Context context) { 
	    this.list = list; 
	    this.context = context; 
	} 
	
	@Override
	public int getCount() { 
	    return list.size(); 
	} 

	@Override
	public Object getItem(int pos) { 
	    return list.get(pos); 
	} 

	@Override
	public long getItemId(int pos) { 
	    //return list.get(pos);
		return 0;
	    //just return 0 if your list items do not have an Id variable.
	} 
	
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    View view = convertView;	    
	    if (view == null) {
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	        view = inflater.inflate(R.layout.custom_layout, null);
	    } 
	    	  
	    
	    TextView listItemNumber = (TextView)view.findViewById(R.id.list_item_number); 
	    listItemNumber.setText(list.get(position).getContactName()); 
	    

	    listItemNumber.setOnClickListener(new View.OnClickListener(){
	        @Override
	        public void onClick(View v) { 

	        	String name = list.get(position).getContactName();
	        	userName = name;
	        	deliveryNumber = list.get(position).getContactNumber();
	        	
	    		Intent intent = new Intent(context,SMSActivity.class);
	    		Bundle bundle = new Bundle();

	    		//Add your data to bundle
	    		bundle.putString("userName", userName);
	    		bundle.putString("deliveryNumber", deliveryNumber);
	    		//Add the bundle to the intent
	    		intent.putExtras(bundle);
	    		context.startActivity(intent);
	        }
	    });	    
	    return view; 
	} 	
			

	}
