package com.example.t0011_;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CampaignDB extends SQLiteOpenHelper {
	// ��������� - ����� ��, ������� � ������ ��
	public final static String DB_NAME = "db";
	public final static String DB_TABLE = "business";
	public final static int DB_VERSION = 1;
	
	// ��������� - ����� ��������
	public final static String KEY_ID = "_id";
	public final static String KEY_NAME = "name";
	public final static String KEY_OFFER = "offer";
	public final static String KEY_TYPE = "type";
	public final static String KEY_START_DATE = "start_date";
	public final static String KEY_END_DATE = "end_date";
	public final static String KEY_RATE = "rate";
	public final static String KEY_IMAGE = "image";
	
	// ������ �� 
	public final SQLiteDatabase db;	;

	// ������� ��� �������� (��� - ������)
	public final static Map<String, Integer> images;
	static{
		images = new HashMap<String, Integer>();
		images.put("lamp", R.drawable.icon_lamp);
		images.put("clock", R.drawable.icon_clock);
		images.put("office", R.drawable.icon_office);
	}
	
	public CampaignDB(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		db = getWritableDatabase();
		fillData();
	}

	// �������� ��
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + DB_TABLE + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_NAME + " TEXT NOT NULL, "
				+ KEY_OFFER + " TEXT NOT NULL, "
				+ KEY_TYPE + " TEXT, "
				+ KEY_START_DATE + " TEXT, "
				+ KEY_END_DATE + " TEXT, "
				+ KEY_RATE + " REAL NOT NULL, "
				+ KEY_IMAGE + " TEXT" + ");";
		db.execSQL(sql);
		//fillData();
	}


	// ���������� �� ��� ��������� ������ ������
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + DB_TABLE ;
		db.execSQL(sql);
		onCreate(db);
	}

	// ���������� ���� ����������� �������� CRUD
	public Cursor getAllRecords(){
		//		String orderBy = KEY_NAME + " ASC";
		return db.query(DB_TABLE, null, null, null, null, null, null); 
	}

	public Cursor getRecordById(String id){
		String selection = KEY_ID + " = " + id;
		return db.query(DB_TABLE, null, selection, null, null, null, null); 
	}
	
	public long insert(ContentValues values){
		return db.insert(DB_TABLE, null, values);
	}
	
	public int update(String id, ContentValues values){
		String whereClause = KEY_ID + " = " + id;
		return db.update(DB_TABLE, values, whereClause, null);
	}
	
	public int delete(String id){
		String whereClause = KEY_ID + " = " + id;
		return db.delete(DB_TABLE, whereClause, null);
	}
	
	// �������� ���������� ���� � ��������� ������
    void fillData()  {
    	new Thread( new Runnable() {			
			@Override
			public void run() {
				String sql = "DROP TABLE IF EXISTS " + DB_TABLE ;
				db.execSQL(sql);
				onCreate(db);
				
		    	String[] img = images.keySet().toArray(new String[0]) ;

		    	String[] companies = { 
		    			"Illitch Iron",
		    			"Company2",
		    			"Office",
		    			"AzovSteel"
		    	};
		    	String[] offers = { "lamp", "clock", "---" , "gadget", "widget", "pad"};
		   	
		    	ContentValues values = new ContentValues(); 
		    	for(int i = 0; i < 16; i++){
			    	values.put(KEY_NAME, companies[i % companies.length]);
			    	values.put(KEY_OFFER, offers[i % offers.length]);
			    	values.put(KEY_IMAGE, img[i % img.length]);
			    	values.put(KEY_RATE, i*0.5);
			    	values.put(KEY_TYPE, "Type");
			    	values.put(KEY_START_DATE, "1.2.2013");
			    	db.insert(DB_TABLE, null, values);
		    	}
				
			}
		}).start();
		
    }
	
}
