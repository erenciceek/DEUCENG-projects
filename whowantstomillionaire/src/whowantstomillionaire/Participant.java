package whowantstomillionaire;

import java.io.*;
import java.util.Scanner;
import java.time.*;
import java.util.*;
public class Participant {
	private String name;
	private String birthday;
	private String phonenumber;
	private String address;
	private boolean joined;
	
	

	public Participant(String n,String b,String pn,String add,boolean joined) {
		this.name = n;
		this.birthday = b;
		this.phonenumber = pn;
		this.address=add;
		this.joined=joined;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	
	public String displayParticipant() {
		return "Name-Surname: "+name+"\n"+"Birthday: "+birthday+"\n"+"Phone Number: "+phonenumber+"\n"+"Address: "+address;
	}
	
	public  int findAge(int currentday,int currentmonth,int currentyear) {
		String[] date = birthday.split("\\.");
		int day,month,year;
		int diffday,diffmonth,diffyear;
		day = Integer.parseInt(date[0]);
		month = Integer.parseInt(date[1]);
		year = Integer.parseInt(date[2]);
	    if(currentday<day) {
	    	diffday = currentday - day+30;
	    	--currentmonth;
	    }
	    else
	    	diffday = currentday - day;
	    if(currentmonth < month) {
	    	diffmonth = currentmonth - month + 12;
	    	--currentyear;
	    }
	    else
	    	diffmonth = currentmonth - month;
	    diffyear = currentyear - year;
		return diffyear;
		
	     
	}
	
	
	
	
	
}
