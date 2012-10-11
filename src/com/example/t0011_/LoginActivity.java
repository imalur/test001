package com.example.t0011_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	
	Button btnLogin;
	Button btnSignUp;
	EditText etLogin;
	EditText etPassword;
	CheckBox chbShowPassword;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);        
        chbShowPassword = (CheckBox) findViewById(R.id.chbShowPassword);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        chbShowPassword.setOnClickListener(this);
        
        TextView tv = (TextView) findViewById(R.id.tvLoginTitle);        
        
        showPassword();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		Intent intent = null;		
		switch(v.getId()){
		// Button LOGIN handler
		case R.id.btnLogin:
			// check input format
			boolean isValid = inputValidator(); 						
			if (! isValid){
				String toast = "Incorrect input format";
				Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
				return;
			}
			// check login correctness
			boolean isLogged = fakeLogin();
			if (!isLogged){
				String toast = "Incorrect Login or Password";
				Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
				return;
			}
			// Login
			intent = new Intent(this, OffersActivity.class);
			startActivity(intent);	
			break;
		// Button SIGNUP handler
		case R.id.btnSignUp:			
			intent = new Intent(this, SignupActivity.class);
			startActivity(intent);			
			break;
		// CheckBox handler - change password's input type
		case R.id.chbShowPassword:
			showPassword();			
			break;
		}
		
	}
	
	private void showPassword(){
		int inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT;
		if (chbShowPassword.isChecked())
			inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
		etPassword.setInputType(inputType);
	}
	
	// check login and Password input format
	private boolean inputValidator(){
		boolean isValid = true;
		String login = etLogin.getText().toString();
		String password = etPassword.getText().toString();
		String regex = "[A-Za-z0-9]{3,10}";
		
		isValid &= TextValidator.Match(login, regex);
		isValid &= TextValidator.Match(password, regex);
		
		return isValid;
	}
	
	// check login and Password correctness (dummy)
	private boolean fakeLogin(){	
		String login = etLogin.getText().toString();
		String password = etPassword.getText().toString();				
		return login.equals("Login") && password.equals("Password");
	}
}
