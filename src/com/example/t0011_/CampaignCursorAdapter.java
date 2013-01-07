package com.example.t0011_;

import java.util.HashMap;
import java.util.Map;

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
	
	// ��������� ������������� �������
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// ��������� ������ (���� ��� ���)
				int offerCol = cursor.getColumnIndex(CampaignDB.KEY_OFFER);
				((TextView) view.findViewById(R.id.tvOfferText)).setText(cursor.getString(offerCol));
				int nameCol = cursor.getColumnIndex(CampaignDB.KEY_NAME);
				((TextView) view.findViewById(R.id.tvCompanyName)).setText(cursor.getString(nameCol));
				int typeCol = cursor.getColumnIndex(CampaignDB.KEY_TYPE);
				((TextView) view.findViewById(R.id.tvType)).setText(cursor.getString(typeCol));
				int rateCol = cursor.getColumnIndex(CampaignDB.KEY_RATE);
				((TextView) view.findViewById(R.id.tvRate)).setText(String.valueOf(cursor.getDouble(rateCol)));
				
				
				// ������� � ����� ������� �������� � int, �� ����� �����, ��� ���� �������� 
				// ����� ��������, � ���������� �������� ��������
				int imgCol = cursor.getColumnIndex(CampaignDB.KEY_IMAGE);				
				String imageName = cursor.getString(imgCol);
				int image = CampaignDB.images.get(imageName);
				((ImageView) view.findViewById(R.id.ivImage)).setImageResource(image);		
				
				// ����
				DateHelper date = new DateHelper();
				int startCol = cursor.getColumnIndex(CampaignDB.KEY_START_DATE);
				String startDate = cursor.getString(startCol);
				String text = "From Start Date";			
				if (date.parse(startDate))
					text = "From " + date;					
				((TextView) view.findViewById(R.id.tvStartDate)).setText(text);
				
				int endCol = cursor.getColumnIndex(CampaignDB.KEY_END_DATE);
				String endDate = cursor.getString(endCol);
				text = "To End Date";
				if (date.parse(endDate))
					text = "To " + date;
				((TextView) view.findViewById(R.id.tvEndDate)).setText(text);
	}
	// ������� ������������� � ���������� ���������
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
		// ��� � ����������� �� position % 2
		int backcolor = R.drawable.listview_blue_gradient;
		if (position % 2 == 0 )
			backcolor = R.drawable.listview_green_gradient;
		view.findViewById(R.id.layoutItem).setBackgroundResource(backcolor);
		return view;
	}


}
