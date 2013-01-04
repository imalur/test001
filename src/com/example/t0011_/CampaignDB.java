package com.example.t0011_;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CampaignDB extends SQLiteOpenHelper {
	// Константы - имена БД, таблицы и версия БД
	public final static String DB_NAME = "mydb";
	public final static String DB_TABLE = "business";
	public final static int DB_VERSION = 1;
	
	// Константы - имена столбцов
	public final static String KEY_ID = "_id";
	public final static String KEY_NAME = "name";
	public final static String KEY_OFFER = "offer";
	public final static String KEY_TYPE = "type";
	public final static String KEY_START_DATE = "start_date";
	public final static String KEY_END_DATE = "end_date";
	public final static String KEY_RATE = "rate";
	public final static String KEY_IMAGE = "image";
	
	// Объект БД 
	public final SQLiteDatabase db;	;

	public CampaignDB(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		db = getWritableDatabase();
		fillData();
	}

	// Создание БД
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + DB_TABLE + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_NAME + " TEXT NOT NULL, "
				+ KEY_OFFER + " TEXT NOT NULL, "
				+ KEY_TYPE + " TEXT, "
				+ KEY_START_DATE + " INTEGER, "
				+ KEY_END_DATE + " INTEGER, "
				+ KEY_RATE + " REAL NOT NULL, "
				+ KEY_IMAGE + " INTEGER" + ");";
		db.execSQL(sql);
		//fillData();
	}


	// Обновление БД при изменении номера версии
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + DB_TABLE ;
		db.execSQL(sql);
		onCreate(db);
	}

	// реализация всех стандартных операций CRUD
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
	
	// тестовое заполнение базы 
    void fillData()  {
		String sql = "DROP TABLE IF EXISTS " + DB_TABLE ;
		db.execSQL(sql);
		onCreate(db);
		
    	int[] images = { 
    			R.drawable.icon_lamp,
    			R.drawable.icon_clock,
    			R.drawable.icon_office
    	};    	
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
	    	values.put(KEY_IMAGE, images[i % images.length]);
	    	values.put(KEY_RATE, i*0.5);
	    	values.put(KEY_TYPE, "Type");
	    	db.insert(DB_TABLE, null, values);
    	}
    }
	
}
