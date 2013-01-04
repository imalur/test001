package com.example.t0011_;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;



public class CampaignProvider extends ContentProvider {
	// ��������� ��� ������������� Uri ���� 
	// content://<authority>/<path>/<id>
	public static final String AUTHORITY = "com.testproject.business";
	public static final String PATH = "companies";
	public static final Uri CAMPAIGN_URI = 
			Uri.parse("content://" + AUTHORITY + "/" + PATH);
	
	// 3. ��������� ��� �������� Uri �� ������������� ��� ��������� �������
	public static final int SINGLE_COMPANY = 1;	// ��������� �������
	public static final int ALL_COMPANIES = 2;	// ������������� �������
	public static final UriMatcher uriMatcher;	
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PATH, ALL_COMPANIES);
		uriMatcher.addURI(AUTHORITY, PATH + "/#", SINGLE_COMPANY);
	}
	
	CampaignDB dbHelper; // ��� ������ � ��	
	
	@Override
	public boolean onCreate() {
		dbHelper = new CampaignDB(getContext());
		return true;
	}
	
	// ����������� ���� MIME
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case SINGLE_COMPANY:
			return "vnd.com.example.cursor.item" + "/" + "vnd." + PATH;
		case ALL_COMPANIES:
			return "vnd.com.example.cursor.dir" + "/" + "vnd." + PATH;
		}
		throw new IllegalArgumentException("Illegal uri " + uri);
	}
	
	// ������ CRUD - ��� thread-safe ����e� synchronized
	@Override
	synchronized public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		switch (uriMatcher.match(uri)) {
		case SINGLE_COMPANY:
			String id = uri.getLastPathSegment();
			return dbHelper.getRecordById(id);
		case ALL_COMPANIES:
			return dbHelper.getAllRecords();
		}
		throw new IllegalArgumentException("Illegal uri " + uri);
	}
	
	@Override
	synchronized public Uri insert(Uri uri, ContentValues values) {
		Uri newUri = null;
		long newId = dbHelper.insert(values);
		if (newId > 0)
			newUri = ContentUris.withAppendedId(CAMPAIGN_URI, newId);
		return newUri;
	}


	@Override
	synchronized public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int cnt = 0;
		if (uriMatcher.match(uri) == SINGLE_COMPANY){
			String id = uri.getLastPathSegment();
			cnt =  dbHelper.update(id, values);
		}
		return cnt;
	}
	
	@Override
	synchronized public int delete(Uri uri, String selection, String[] selectionArgs) {
		int cnt = 0;
		if (uriMatcher.match(uri) == SINGLE_COMPANY){
			String id = uri.getLastPathSegment();
			cnt =  dbHelper.delete(id);
		}
		return cnt;
	}


	

}
