package modelPackage;

import java.time.LocalDate;

public class Time {
	private LocalDate date;
	private int hour;
	private int min;
	
	public Time(LocalDate date,int hour,int min) throws Exception{
		if(hour<0||hour>=24)
			throw new Exception("Hour Invalid");
		if(min<0||min>=60)
			throw new Exception("Min Invalid");
		this.date = date;
		this.min = min;
		this.hour = hour;
	}


	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
	
	public String toString() {
		String s = this.date.toString()+"\n";
		String t;
		if(this.hour<10) {
			t = "0"+this.hour+":";
		}
		else
			t = this.hour+":";
		if(this.min<10){
			t = t+"0"+this.min;
		}
		else
			t = t+this.min;
		s = s+t;
		return s+"\n";
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Time))
			return false;
		else {
			if(((Time)(obj)).date.equals(this.date)&&((Time)(obj)).getMin()==this.min&&((Time)(obj)).getHour()==this.hour)
				return true;
			return false;		
		}
	}

}
