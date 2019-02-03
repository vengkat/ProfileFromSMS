package com.venk.profilefromsms;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBmethod extends SQLiteOpenHelper {

	   public static final String DATABASE_NAME = "MobileAssistant.db";
	   public static final String CONTACTS_TABLE_NAME = "ContactList";
	   public static final String CONTACTS_COLUMN_ID = "id";
	   public static final String CONTACTS_COLUMN_MobileNumber = "MobileNumber";

	   public DBmethod(Context context)
	   {
	      super(context, DATABASE_NAME , null, 1);
	   }

	   @Override
	   public void onCreate(SQLiteDatabase db) {
	        db.execSQL("CREATE TABLE IF NOT EXISTS ContactList(SlN0 integer primary key autoincrement NOT NULL, Name VARCHAR  NOT NULL, MobileNumber VARCHAR  NOT NULL);");	        
	   }

	   @Override
	   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	      // TODO Auto-generated method stub
	      db.execSQL("DROP TABLE IF EXISTS ContactList");
	      onCreate(db);
	   }
	   
	   public void AddContact (String name, String number)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();		   
	      db.execSQL("CREATE TABLE IF NOT EXISTS ContactList(SlN0 integer primary key autoincrement NOT NULL, Name VARCHAR  NOT NULL, MobileNumber VARCHAR  NOT NULL);");	    		        
	      db.execSQL("INSERT INTO ContactList (Name, MobileNumber ) " + " values( "
				       + "\"" + name + "\"" + " , " + number + ") ;");
	   }
	   
	   public void Delete (String phone)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
		   
	      //db.delete("ContactList", 
	      //"MobileNumber = ? ", 
	      //new String[] { Num });
	      
	      db.delete("ContactList", 
	    	      "MobileNumber = ? ", 
	    	      new String[] { phone });
	   }
	   
		 public ArrayList<String> GetNumberList()
		   {
			   SQLiteDatabase db = this.getWritableDatabase();
			   ArrayList<String> list = new ArrayList<String>();
			   Cursor cur=db.rawQuery("SELECT MobileNumber FROM ContactList", null);					
				cur.moveToFirst();
				while(!cur.isAfterLast())
				{								
					list.add(cur.getString(0));
					//list.add(cur.getString(1));
					cur.moveToNext();
				}		   //
			   return list;
		   }
		 
		 public String GetContactName(String number)
		   {
			   SQLiteDatabase db = this.getWritableDatabase();
			   String ContactName = "";
			   Cursor cur=db.rawQuery("SELECT Name FROM ContactList where MobileNumber = "+ "'" + number + "'" , null);					
				cur.moveToFirst();
				while(!cur.isAfterLast())
				{								
					ContactName = cur.getString(0);
					cur.moveToNext();
				}		   //
			   return ContactName;
		   }
		 
		 public boolean IfNumberExists(String number)
		   {
			   SQLiteDatabase db = this.getWritableDatabase();
			   boolean result = false;
			   Cursor cur=db.rawQuery("SELECT Name FROM ContactList where MobileNumber = "+ "'" + number + "'" , null);					
				cur.moveToFirst();
				while(!cur.isAfterLast())
				{					
					result = true;					
					cur.moveToNext();
				}		   //
			   return result;
		   }
		

		   public void UpdateKey (String newKey)
		   {
		      SQLiteDatabase db = this.getWritableDatabase();		   
		      ContentValues cv = new ContentValues();
		      cv.put("Key",newKey);
		      db.update("AuthKey", 
		    		  cv,
		    		  "SlN0=1111",
		    		  null);
		   }
		 public boolean KeyExists()
		   {
			 boolean result = false;
			   SQLiteDatabase db = this.getWritableDatabase();			   
			   Cursor cur=db.rawQuery("SELECT Key FROM AuthKey where SlN0 = 1111" , null);					
				cur.moveToFirst();
				while(!cur.isAfterLast())
				{								
					result = true;
				}		   //
			   return result;
		   }
		 
		 public String GetKey()
		   {
			 boolean result = false;
			 String key = "";
			   SQLiteDatabase db = this.getWritableDatabase();
			   //db.execSQL("DROP TABLE AuthKey;");
			   db.execSQL("CREATE TABLE IF NOT EXISTS AuthKey(SlN0 integer primary key NOT NULL, Key VARCHAR  NOT NULL);");
			   			   
			   Cursor cur=db.rawQuery("SELECT Key FROM AuthKey where SlN0 = 1111" , null);					
				cur.moveToFirst();
				while(!cur.isAfterLast())
				{								
					key = cur.getString(0);
					result = true;
					cur.moveToNext();
				}	
				if(!result)
				{
					db.execSQL("INSERT INTO AuthKey (SlN0, Key ) values( "
						       + "1111 , '0000' );");
					key = "";
				}
			   return key;
		   }
		 
		 

	}