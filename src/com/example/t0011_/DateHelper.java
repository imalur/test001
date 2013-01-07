package com.example.t0011_;

import android.text.TextUtils;

public class DateHelper {
	
	private int day;
	private int month;
	private int year;
	
	public DateHelper(){
		day = 1;
		month = 0;
		year = 1970;
	}
	
	public boolean parse(String date){
		// проверка на null 
		if (date == null || TextUtils.isEmpty(date))
			return false;
		// проверка на соответствие шаблону	
		if (!TextValidator.match(date, TextValidator.DATE_REGEX))
			return false;
		// разбираем дату на составляющие регулярным выражением
		String[] dateArray = date.split("\\.");
		int inputDay = Integer.valueOf(dateArray[0]); 
		int inputMonth = Integer.valueOf(dateArray[1]);  
		int inputYear = Integer.valueOf(dateArray[2]);
		if (inputDay <= 0 || inputDay > 31 
				|| inputMonth <= 0 || inputMonth > 12
				|| inputYear < 0 )
			return false;
		this.day = inputDay;
		this.month = inputMonth - 1;
		this.year = inputYear;
		return true;		
	}
	
	@Override
	public String toString() {
		return day + "." + (month + 1) + "." + year;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
}
