package com.example.t0011_;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CampaignCursorAdapter extends CursorAdapter{
	
	Context context;
	
	public CampaignCursorAdapter(Context context, Cursor c) {
		super(context, c);
		this.context = context;
	}
	
	// заполняем представление данными
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// установка данных (пока без дат)
				int offerCol = cursor.getColumnIndex(CampaignDB.KEY_OFFER);
				((TextView) view.findViewById(R.id.tvOfferText)).setText(cursor.getString(offerCol));
				int nameCol = cursor.getColumnIndex(CampaignDB.KEY_NAME);
				((TextView) view.findViewById(R.id.tvCompanyName)).setText(cursor.getString(nameCol));
				int typeCol = cursor.getColumnIndex(CampaignDB.KEY_TYPE);
				((TextView) view.findViewById(R.id.tvType)).setText(cursor.getString(typeCol));
				int rateCol = cursor.getColumnIndex(CampaignDB.KEY_RATE);
				((TextView) view.findViewById(R.id.tvRate)).setText(String.valueOf(cursor.getDouble(rateCol)));
				int imgCol = cursor.getColumnIndex(CampaignDB.KEY_IMAGE);
				((ImageView) view.findViewById(R.id.ivImage)).setImageResource(cursor.getInt(imgCol));		
				
		
	}
	// создаем представление с конкретной разметкой
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.item, parent, false);
		bindView(v, context, cursor);
		return v;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);		
		// фон в зависимости от position % 2
		int backcolor = R.drawable.listview_blue_gradient;
		if (position % 2 == 0 )
			backcolor = R.drawable.listview_green_gradient;
		view.findViewById(R.id.layoutItem).setBackgroundResource(backcolor);
		return view;
	}


}
