package com.example.t0011_;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class CampaignAdapter extends BaseAdapter{
	Context ctx;
	LayoutInflater inflater;
	ArrayList<Campaign> objects;

	CampaignAdapter(Context context, ArrayList<Campaign> campaign){
		ctx = context;
		objects = campaign;
		inflater = 
				(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		return objects.size();	
	}

	@Override
	public Object getItem(int position) { 
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.item , parent, false);
		
		// текущий элемент
		Campaign campaign = (Campaign) getItem(position);
		
		// фон в зависимости от position % 2
		int backcolor = R.drawable.listview_blue_gradient;
		if (position % 2 == 0 )
			backcolor = R.drawable.listview_green_gradient;
		((LinearLayout) view.findViewById(R.id.layoutItem)).setBackgroundResource(backcolor);
		
		// установка данных (пока без дат)
		((TextView) view.findViewById(R.id.tvOfferText)).setText(campaign.getOffer());
		((TextView) view.findViewById(R.id.tvCompanyName)).setText(campaign.getCompanyName());
		((TextView) view.findViewById(R.id.tvType)).setText(campaign.getType());
		((TextView) view.findViewById(R.id.tvRate)).setText(String.valueOf(campaign.getRate()));
		((ImageView) view.findViewById(R.id.ivImage)).setImageResource(campaign.getImage());		
		
		return view;
	}

}
