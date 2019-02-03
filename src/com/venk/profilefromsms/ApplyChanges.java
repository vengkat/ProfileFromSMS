package com.venk.profilefromsms;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

public class ApplyChanges extends Activity {	
	
	public void Apply(String message, String senderNum, Context context)
	{
		
		AudioManager mobilemode = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
		
	    if(message.toLowerCase().contains("loud"))
	    {
	    	//mobilemode.setStreamVolume(AudioManager.STREAM_RING, 7, AudioManager.FLAG_ALLOW_RINGER_MODES|AudioManager.FLAG_PLAY_SOUND);
	    	mobilemode.setStreamVolume(AudioManager.STREAM_RING, mobilemode.getStreamMaxVolume(AudioManager.STREAM_RING), AudioManager.FLAG_ALLOW_RINGER_MODES|AudioManager.FLAG_PLAY_SOUND);
	    	//mobilemode.getStreamMaxVolume(AudioManager.STREAM_RING);	    		        
	    }
	    else if(message.toLowerCase().contains("silent")){	        	
	    	mobilemode.setStreamVolume(AudioManager.STREAM_RING,AudioManager.RINGER_MODE_SILENT,0);	        		    	
	    }		   
     }
	
	
}
