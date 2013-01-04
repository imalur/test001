package com.example.t0011_;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class OffersActivity extends FragmentActivity 
	implements OnClickListener, LoaderCallbacks<Cursor>{

	CampaignCursorAdapter adapter;
	ListView lvMain;
	Button btnAccount;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
                
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        
        // вместо курсора вторым параметром передаем null
        adapter = new CampaignCursorAdapter(this, null);
        // инициализация менеджера загрузки
        final int LOADER_ID = 0; 
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(adapter);
        
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.btnAccount :
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;
		}
		
	}

	// необходимо реализовать три метода для LoaderManager.LoaderCallbacks<Cursor>
    // 1. создание loader-курсора
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, CampaignProvider.CAMPAIGN_URI,
				null, null, null, null);
	}
	// 2. окончание загрузки - подмена курсора
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		adapter.swapCursor(cursor);
		
	}
	
	// 3. отмена загрузки - установка курсора в null
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
		
	}
    
}
