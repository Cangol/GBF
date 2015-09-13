package com.azhuoinfo.gbf.db;

import mobi.cangol.mobile.db.CoreSQLiteOpenHelper;
import mobi.cangol.mobile.db.DatabaseUtils;
import mobi.cangol.mobile.logging.Log;
import com.azhuoinfo.gbf.model.Message;
import com.azhuoinfo.gbf.utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper extends CoreSQLiteOpenHelper {
	private static final String TAG = "DataBaseHelper";
	private static final String DATABASE_NAME = Constants.DATABASE_NAME;
	private static final int DATABASE_VERSION = Constants.DATABASE_VERSION;
	private static DatabaseHelper dataBaseHelper;
	
	public static DatabaseHelper createDataBaseHelper(Context context){
		if(null==dataBaseHelper){	
			dataBaseHelper=new DatabaseHelper();
			dataBaseHelper.open(context);
		}	
		return dataBaseHelper;
	}

	@Override
	public String getDataBaseName() {
		return DATABASE_NAME;
	}

	@Override
	public int getDataBaseVersion() {
		return DATABASE_VERSION;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG,"onCreate");
		DatabaseUtils.createTable(db, Message.class);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG,"onUpgrade "+oldVersion+"->"+newVersion);
		if(db.getVersion()<DATABASE_VERSION){
			//DatabaseUtils.dropTable(db, Goddess.class);
		}
	}



}
