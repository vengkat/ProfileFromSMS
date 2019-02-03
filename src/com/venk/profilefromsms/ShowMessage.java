package com.venk.profilefromsms;

import android.content.Context;
import android.widget.Toast;

public class ShowMessage {	
	int duration = Toast.LENGTH_LONG;

    /*public ShowMessage(Context contxt, CharSequence msg)
    {
    	context = contxt;
    	message = msg;
    }
    */
    
    
    public void show(Context  context, CharSequence message)
    {    	
    	Toast toast = Toast.makeText(context, 
                message, duration);
    	toast.show();
    }
    
}
