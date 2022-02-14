package modelPackage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ObserversInterpaces.Observer;
import ObserversInterpaces.Observerable;
import controller.JDBController;

//import ObserversInterpaces.Observer;
//import ObserversInterpaces.Observerable;

public class Costumer extends Person implements Observer {
	private static int c_id =1;
	private String username;
	private String password;
	private String msg;
	private Appointment appointment;
	
	
	//////////////////////////////////////////////////////
	////// Observer funcs
		@Override
		public void update(Observerable ob, Object date) {
				LocalDate d = (LocalDate)date;
				this.setMsg("Attention ! , your appointment on date "+d.toString()+" has been canceled ");
				System.out.println(" Observer changed msg! for "+this.username);
			}
	
	public Costumer(String username,String password,String name) {
		super(name,c_id);
		this.username = username;
		this.password = password;
		this.appointment = null;
		msg = "";
		//flag = false ; 
		c_id++;
	}
	
	
	public Costumer(String username,String password,String name,int id,String msg) {
		super(name,id);
		this.username = username;
		this.password = password;
		this.appointment = null;
		this.msg = msg; 
	}
	
	
	public String getMsg() {
		return msg;
	}
	
	public void clearMsg() { 
		this.msg = "";
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	 

	public static int getC_id() {
		return c_id;
	}


	public Appointment getAppointment() {
		return appointment;
	}


	public static void setC_id(int cid) {
		c_id = cid;
	}
	
	public boolean setAppointment(Appointment a) {
		this.appointment = a;
		return true;
	}
	
	
	public boolean equals(Object obj) {
		if(obj instanceof Costumer) {
			if(((Costumer)(obj)).getUsername().equals(this.username))
				return true;
		}
		return false;
	}
	
	public String toString() {
		String s = super.toString()+"username:"+this.username;
		return s;
	}

	public String getName() {
		return super.getName();
	}

	public void setName(String name) {
		super.setName(name);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public static void decreaseC_Id() {
		c_id--;		
	}


	

}
