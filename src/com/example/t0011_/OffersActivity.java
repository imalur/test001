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
    	Campaign c = new Campaign();
    	c.companyName = "Illitch Iron";
    	c.offer = "lamp";
    	c.image = R.drawable.icon_lamp;
    	c.rate = 1;
    	campaign.add(c);
    	
    	c = new Campaign();
    	c.companyName = "Company2";
    	c.offer = "clock";
    	c.image = R.drawable.icon_clock;
    	c.rate = 2;
    	campaign.add(c);
    	
    	c = new Campaign();
    	c.companyName = "Office";
    	c.offer = "---";
    	c.image = R.drawable.icon_office;
    	c.rate = 4.5;
    	campaign.add(c);
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
