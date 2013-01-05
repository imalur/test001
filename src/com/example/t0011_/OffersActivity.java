package com.example.t0011_;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
        registerForContextMenu(lvMain);
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
    
	// == КОНТЕКСТНОЕ ИЕНЮ ДЛЯ ОПЕРАЦИЙ CRUD
	// константы для идентификации пунктов меню
    final int CNTX_MENU_ADD = 0;
    final int CNTX_MENU_EDT = 1;
    final int CNTX_MENU_DEL = 2;

    // регистрация представления для меню – в методе onCreate активити
    
    // создание контекстного меню
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	// добавляем три пункта
    	menu.add(0, CNTX_MENU_ADD, 0, "Add New Record");
    	menu.add(0, CNTX_MENU_EDT, 0, "Edit Record");
    	menu.add(0, CNTX_MENU_DEL, 0, "Delete Record");    	
    }
    
    // обработчик выбора пункта меню  
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// получаем id выбранного элемента (значение поля _id из БД) 
    	AdapterContextMenuInfo  info = (AdapterContextMenuInfo) item.getMenuInfo();
    	long id = info.id; 
    	// текущее действие
    	int mode = CRUDDialog.MODE_NONE;
    	// обработка пунктов меню
    	switch (item.getItemId()) {
		case CNTX_MENU_ADD:			
			mode = CRUDDialog.MODE_ADD;
			break;
		case CNTX_MENU_EDT:
			mode = CRUDDialog.MODE_EDT;
			break;
		case CNTX_MENU_DEL:
			mode = CRUDDialog.MODE_DEL;
			break;
		default:
			break;
		}
    	// запускаем диалоговое окно для редактирования и передаем данные о режиме и id
        Bundle args = new Bundle();
        args.putLong(CRUDDialog.KEY_ID, id);
        args.putInt(CRUDDialog.KEY_MODE, mode);
        
        CRUDDialog dialog = new CRUDDialog();
        dialog.setArguments(args);

        // начало транзакции для динамического добавления фрагмента в приложение
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(dialog, "DIALOG_TAG");
        ft.commit();
    	
    	return super.onContextItemSelected(item);
    }

}
