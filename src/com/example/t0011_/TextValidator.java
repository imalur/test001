package com.example.t0011_;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextValidator {
	// check input string with regular expression
	public static boolean match(String text, String regex){				
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);		
		return matcher.matches();
	}

}
