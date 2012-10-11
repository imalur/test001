package com.example.t0011_;

import java.util.Date;

public class Campaign {
	private String mCompanyName = "Company Name";
	private String mOffer = "Offer";
	private String mType = "Type";
	private Date mStartDate;
	private Date mEndDate;
	private double mRate = 1.;
	private int mImage = R.drawable.icon_clock;
	
	public void setCompanyName(String companyName){
		this.mCompanyName = companyName;
	}
	public String getCompanyName(){
		return this.mCompanyName;
	}
	
	public void setOffer(String offer){
		this.mOffer = offer;
	}
	public String getOffer(){
		return this.mOffer;
	}
	
	public void setType(String type){
		this.mType = type;
	}
	public String getType(){
		return this.mType;		
	}
	
	public void setStartDate(Date startDate){
		this.mStartDate = startDate;
	}
	public Date getStartDate(){
		return this.mStartDate;		
	}
	
	public void setEndDate(Date endDate){
		this.mEndDate = endDate;
	}
	public Date getEndDate(){
		return this.mEndDate;		
	}
	
	public void setRate(double rate){
		this.mRate = rate;
	}
	public double getRate(){
		return this.mRate;		
	}
	
	public void setImage(int image){
		this.mImage = image;
	}
	public int getImage(){
		return this.mImage;		
	}

}
