package com.example.t0011_;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CRUDDialog extends DialogFragment implements LoaderCallbacks<Cursor>{
	
	// константы режима 
	public static final int MODE_ADD = 0;
	public static final int MODE_EDT = 1;
	public static final int MODE_DEL = 2;	
	public static final int MODE_NONE = -1;	
	// константы-ключи передаваемых в диалог данных
	public static final String KEY_MODE = "mode";
	public static final String KEY_ID = "id";

	private long id = -1;
	private int mode = MODE_NONE;
		
	EditText etName;
	EditText etOffer;
	EditText etType;
	EditText etRate;
	TextView tvStartDate;
	TextView tvEndDate;
	Spinner spinnerImage;
	View crudView;

	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        if(mode == MODE_EDT){
            Uri uri = CampaignProvider.CAMPAIGN_URI;
            String pathSegment = Long.toString(id);
            uri = Uri.withAppendedPath(uri, pathSegment);
            return new CursorLoader(getActivity(), uri, null, null, null, null);
        }
        else
            return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
        // заполнение полей редактирования данными
        if(data.moveToFirst()){
        	
        	int nameCol = data.getColumnIndex(CampaignDB.KEY_NAME);
            etName.setText(data.getString(nameCol));
            int offerCol = data.getColumnIndex(CampaignDB.KEY_OFFER);
            etOffer.setText(data.getString(offerCol));
            int typeCol = data.getColumnIndex(CampaignDB.KEY_TYPE);
            etType.setText(data.getString(typeCol));
            int rateCol = data.getColumnIndex(CampaignDB.KEY_RATE);
            etRate.setText(data.getString(rateCol));
            
            int imgCol = data.getColumnIndex(CampaignDB.KEY_IMAGE);
            String imgName = data.getString(imgCol);
            int pos = ((ArrayAdapter<String>)spinnerImage.getAdapter()).getPosition(imgName); 
            spinnerImage.setSelection(pos);
            
			DateHelper dateHelper = new DateHelper();
			int startCol = data.getColumnIndex(CampaignDB.KEY_START_DATE);
			String startDate = data.getString(startCol);
			String text = "Start Date";
			if (dateHelper.parse(startDate))
				text = startDate;					
			tvStartDate.setText(text);
			
			
			int endCol = data.getColumnIndex(CampaignDB.KEY_END_DATE);
			String endDate = data.getString(endCol);
			text = "End Date";
			if (dateHelper.parse(endDate))
				text = endDate;
			tvEndDate.setText(text);
        }

		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        
        etName = (EditText) crudView.findViewById(R.id.etName);
        etOffer = (EditText) crudView.findViewById(R.id.etOffer);
        etType = (EditText) crudView.findViewById(R.id.etType);
        etRate = (EditText) crudView.findViewById(R.id.etRate);
        spinnerImage = (Spinner) crudView.findViewById(R.id.spinnerImage);
        tvStartDate = (TextView) crudView.findViewById(R.id.tvStartDate);
        tvStartDate.setOnClickListener(dateClickListener);
        tvEndDate = (TextView) crudView.findViewById(R.id.tvEndDate);
        tvEndDate.setOnClickListener(dateClickListener);
        
        ArrayAdapter<String> adapter;        
        String[] img = CampaignDB.images.keySet().toArray(new String[0]) ;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,img);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImage.setAdapter(adapter);
        spinnerImage.setPrompt("Images");
        
        getLoaderManager().initLoader(0, null, this);
      
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	    	
        // инициализация переданных в диалог режима и id
        Bundle bundle = getArguments();
        if(bundle!=null){
            if(bundle.containsKey(KEY_MODE))
                mode = bundle.getInt(KEY_MODE);
            if(bundle.containsKey(KEY_ID))
                id = bundle.getLong(KEY_ID);
        }
 
        // создаем диалоговое окно
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());        
        crudView = getActivity().getLayoutInflater().inflate(R.layout.crud,null);                
        // изменение внешнего вида окна в зависимости от режима
        switch(mode){
            case MODE_ADD:
                dialogBuilder.setView(crudView);
                dialogBuilder.setTitle("Add Contact");
                break;
            case MODE_EDT:
                dialogBuilder.setView(crudView);
                dialogBuilder.setTitle("Edit Contact");
                break;
            case MODE_DEL:
                dialogBuilder.setTitle("Delete Contact");
                dialogBuilder.setMessage("Are you sure want to delete?");
                break;
        }
 
        // обработчики нажатия кнопок 
        dialogBuilder.setNegativeButton("Cancel", null);        
        dialogBuilder.setPositiveButton("OK", btnOkClickListener);
 
        return dialogBuilder.create();
    }

 // Обработчик нажатия на кнопку OK
    OnClickListener btnOkClickListener = new OnClickListener() {
    	 
        @Override
        public void onClick(DialogInterface dialog, int which) {

            // установка занчений для вставки и удаления записи
            ContentValues contentValues = new ContentValues();
            contentValues.put(CampaignDB.KEY_NAME, etName.getText().toString());
            contentValues.put(CampaignDB.KEY_OFFER, etOffer.getText().toString());
            contentValues.put(CampaignDB.KEY_TYPE, etType.getText().toString());
            contentValues.put(CampaignDB.KEY_RATE, etRate.getText().toString());
            contentValues.put(CampaignDB.KEY_IMAGE, spinnerImage.getSelectedItem().toString());            

            DateHelper dateStart = new DateHelper();
            if (dateStart.parse(tvStartDate.getText().toString()))
            	contentValues.put(CampaignDB.KEY_START_DATE, dateStart.toString());
            DateHelper dateEnd = new DateHelper();
            if (dateEnd.parse(tvEndDate.getText().toString()))
            	contentValues.put(CampaignDB.KEY_END_DATE, dateEnd.toString());
            
            switch(mode){
                case MODE_ADD:
                    // операция вставки через поставщик контента
                    getActivity().getContentResolver().insert(
                    		CampaignProvider.CAMPAIGN_URI, contentValues);
                    break;

                case MODE_EDT:
                	// операция редактирования через поставщик контента
                    // создаем полный uri с учетом id 
                    String pathSegment = Long.toString(id);
                    Uri uri = Uri.withAppendedPath(CampaignProvider.CAMPAIGN_URI, pathSegment);
                    getActivity().getContentResolver().update(uri, contentValues, null, null);
                    break;
                case MODE_DEL:
                	// операция удаления через поставщик контента
                	pathSegment = Long.toString(id);
                	uri = Uri.withAppendedPath(CampaignProvider.CAMPAIGN_URI, pathSegment);
                    getActivity().getContentResolver().delete(uri, null, null);
            }

            // перезапуск loader главной активити для обновления listview 
            getActivity().getSupportLoaderManager().restartLoader(
            		0, null, (LoaderCallbacks<Cursor>)getActivity());
        }		
    };

    // обработчик нажатия на дату
    View.OnClickListener dateClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {			
			final TextView textView = (TextView) v;
			DateHelper date = new DateHelper();
			date.parse(textView.getText().toString());
			
			OnDateSetListener startCallBack = new OnDateSetListener() {					
			    public void onDateSet(DatePicker view, int year, int monthOfYear,
			        int dayOfMonth) {			      
			    	textView.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
			    };
			};	
			
			DatePickerDialog dialog = new DatePickerDialog(
					getActivity(), 
					startCallBack, 
					date.getYear(),
					date.getMonth(),
					date.getDay());
			dialog.show();				
			

			
			
		}
    	
    };
    
}
