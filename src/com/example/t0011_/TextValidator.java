package com.example.t0011_;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextValidator {
	public static final String DATE_REGEX = "[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}";	
	public static final String LOGIN_REGEX = "[A-Za-z0-9]{3,10}";
	
	// check input string with regular expression
	public static boolean match(String text, String regex){				
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);		
		return matcher.matches();
	}

}
