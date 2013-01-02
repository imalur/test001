package com.example.t0011_;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class OffersActivity extends Activity implements OnClickListener{

	ArrayList<Campaign> campaign = new ArrayList<Campaign>();
	CampaignAdapter adapter;
	
	Button btnAccount;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
                
        btnAccount = (Button) findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);
        
        // prepare listView
        fillData();
        adapter = new CampaignAdapter(this, campaign);
        
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(adapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        
        return true;
    }
    
    // create fake data for ListView
    void fillData()  {
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
   	
    	Campaign c; 
    	for(int i =0; i < 16; i++){
	    	c= new Campaign();
	    	c.setCompanyName(companies[i % companies.length]);
	    	c.setOffer(offers[i % offers.length]);
	    	c.setImage(images[i % images.length]);
	    	c.setRate(i*0.5);
	    	campaign.add(c);
    	}
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
    
}
