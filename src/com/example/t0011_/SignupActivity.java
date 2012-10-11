package com.example.t0011_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SignupActivity extends Activity implements OnClickListener{

	Spinner spinnerSex;
	Spinner spinnerCountry;
	Button btnAccept;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        spinnerSex = (Spinner) findViewById(R.id.spinnerSex);
        spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        
        btnAccept.setOnClickListener(this);
        
        // fill spinners
        String[] countries = {"Ukraine", "Russia", "USA", "China"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        spinnerCountry.setAdapter(adapter);
        spinnerCountry.setPrompt("Country");
        //spinnerCountry.setSelection(0);
        
        String[] sex = {"male", "female"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sex);
        spinnerSex.setAdapter(adapter);
        spinnerSex.setPrompt("Sex");
        //spinnerSex.setSelection(0);        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_signup, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, OffersActivity.class);
		startActivity(intent);	
		
	}
}
