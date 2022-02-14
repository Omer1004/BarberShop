package modelPackage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import ObserversInterpaces.Observer;
import ObserversInterpaces.Observerable;
import controller.Controller;

public class Model{
	
	private String barberShopName;
	private HashSet<Costumer> allCostumers;
	private int numOfCostumers;
	private Controller controller;
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//// Normal funktions
	public Model(String barberShopName,String nameOfBarber) {
		this.barberShopName = barberShopName;
		this.allCostumers = new HashSet<Costumer>();
		this.allCostumers.add(new Costumer("admin","1234",nameOfBarber));
		this.numOfCostumers = 0;
		
	}
	public void setController(Controller c) {
		this.controller = c;
	}
	
	public Controller getController() {
		return this.controller;
	}


	@Override
	public String toString() {
		return "Barber Shop Name: " + barberShopName + "\nallCostumers:\n" + getAllCostumersToString()
				+ "\nNum Of Costumers: " + numOfCostumers + "\n";
	}
	
	public ArrayList<Costumer> getAllCostumers(){
		ArrayList<Costumer> arrC = new ArrayList<Costumer>();
		arrC.addAll(this.allCostumers);
		return arrC;
	}

	private String getAllCostumersToString() {
		StringBuffer sb = new StringBuffer();
		for(Costumer c : this.allCostumers) {
			sb.append(c.toString()+"\n");
		}
		return sb.toString();
	}


	public String getBarberShopName() {
		return barberShopName;
	}


	public void setBarberShopName(String barberShopName) {
		this.barberShopName = barberShopName;
	}



	public int getNumOfCostumers() {
		return numOfCostumers;
	}


	public void setNumOfCostumers(int numOfCostumers) {
		this.numOfCostumers = numOfCostumers;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	//add ons
	
	public boolean addCostumer(Costumer c) {
		if(this.allCostumers.add(c)) {
			this.numOfCostumers++;
			return true;
		}
		return false;
	}
	
	public boolean costumerExistsInModel(String username, String password) {
			for(Costumer c : this.allCostumers) {
				if(c.getUsername().equals(username)&&c.getPassword().equals(password))
					return true;
			}
			return false;
	}
	
	public Costumer findCostumerFromId(int id) {
		for(Costumer c : this.allCostumers) {
			if(c.getId()==id)
				return c;
		}
		return null;
	}
	
	public String getAppointmentCostumerToString(int idOfLoggedCostumer) {
		Costumer c = findCostumerFromId(idOfLoggedCostumer);
		if(c!=null) {
			if(c.getAppointment()!=null)
					return c.getAppointment().toString();
			return "there is no Appointment to this client";
		}
		return "costumer is not in system";
	}
	
	
	public boolean deleteAppointmentForLoggedInCostumer(int idOfLoggedCostumer) {
		Costumer c = findCostumerFromId(idOfLoggedCostumer);
		if(c==null) {
			return false;
		}
		else {
			c.setAppointment(null);
			return true;
		}
	}
	public int getIdOfCostumer(String username, String password) {
		for(Costumer c : this.allCostumers) {
			if(c.getUsername().equals(username)&&c.getPassword().equals(password))
				return c.getId();
		}
		return -1;
	}
	public boolean isUsernameExists(String username) {
		for(Costumer c : this.allCostumers) {
			if(c.getUsername().equals(username))
				return true;
		}
		return false;
	}
	public void decreaseC_Id_FromCostumer() {
		Costumer.decreaseC_Id();
	}
	public void removeCostumer(Costumer c) {
		this.allCostumers.remove(c);
		this.numOfCostumers--;
	}
	
	public ArrayList<String> getAllTimesToSetAppointments(){
		ArrayList<String> s = new ArrayList<String>();
		for(int i=0;i<10;i++) {
			if(i+8>=10) {
				s.add((i+8)+":00");
				s.add((i+8)+":30");
			}
			else {
				s.add("0"+(i+8)+":00");
				s.add("0"+(i+8)+":30");
			}
		}
		return s;
		
		
	}
	public void addAppointmentToCostumer(int idOfLoggedCostumer, Appointment a) {
		Costumer c = findCostumerFromId(idOfLoggedCostumer);
		c.setAppointment(a);
		
	}
	public void finishBoot() {
		int maxId=1;
		for(Costumer c : this.allCostumers) {
			if(c.getAppointment()!=null&&maxId<c.getAppointment().getAppointment_id())
				maxId = c.getAppointment().getAppointment_id();
		}
		Appointment.setA_id(maxId+1);
	}
	public void deleteAppointmentInTime(Time t) {
		for(Costumer c : this.allCostumers) {
			if(c.getAppointment()!=null&& c.getAppointment().getTimeOfApp().equals(t))
				c.setAppointment(null);
			}
		}

	public ArrayList<Observer> getCurrectUsersToMsg(LocalDate time) {
		ArrayList<Observer> temp = new ArrayList<Observer>();
		for (Costumer c :this.allCostumers) {
			if(c.getAppointment()!=null && c.getAppointment().getTimeOfApp().getDate().equals(time)) {
				temp.add(c); 
			}
		}
		return temp;
	}
	public void deleteAppointmentInDate(LocalDate date) {
		for(Costumer c : this.allCostumers) {
			if(c.getAppointment()!=null&& c.getAppointment().getTimeOfApp().getDate().equals(date))
				c.setAppointment(null);
		}
	}
	
	//synchronized = Only one thread can use the same methud in 1 time
}

	

